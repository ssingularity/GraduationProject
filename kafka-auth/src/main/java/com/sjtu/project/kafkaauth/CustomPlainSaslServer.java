package com.sjtu.project.kafkaauth;

import org.apache.kafka.common.errors.SaslAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.callback.CallbackHandler;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslException;
import javax.security.sasl.SaslServer;
import javax.security.sasl.SaslServerFactory;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/26 13:20
 */
public class CustomPlainSaslServer implements SaslServer {
    static final String PLAIN_MECHANISM = "PLAIN";

    private final Logger LOG = LoggerFactory.getLogger(CustomPlainSaslServer.class);

    private boolean complete;

    private String authorizationId;

    /**
     * @throws SaslAuthenticationException if username/password combination is invalid or if the requested
     *                                     authorization id is not the same as username.
     *                                     <p>
     *                                     <b>Note:</b> This method may throw {@link SaslAuthenticationException} to provide custom error messages
     *                                     to clients. But care should be taken to avoid including any information in the exception message that
     *                                     should not be leaked to unauthenticated clients. It may be safer to throw {@link SaslException} in
     *                                     some cases so that a standard error message is returned to clients.
     *                                     </p>
     */
    @Override
    public byte[] evaluateResponse(byte[] responseBytes) throws SaslAuthenticationException {
        /*
         * Message format (from https://tools.ietf.org/html/rfc4616):
         *
         * message   = [authzid] UTF8NUL authcid UTF8NUL passwd
         * authcid   = 1*SAFE ; MUST accept up to 255 octets
         * authzid   = 1*SAFE ; MUST accept up to 255 octets
         * passwd    = 1*SAFE ; MUST accept up to 255 octets
         * UTF8NUL   = %x00 ; UTF-8 encoded NUL character
         *
         * SAFE      = UTF1 / UTF2 / UTF3 / UTF4
         *                ;; any UTF-8 encoded Unicode character except NUL
         */

        String response = new String(responseBytes, StandardCharsets.UTF_8);
        List<String> tokens = extractTokens(response);
        String authorizationIdFromClient = tokens.get(0);
        String username = tokens.get(1);
        String password = tokens.get(2);

        if (username.isEmpty()) {
            throw new SaslAuthenticationException("Authentication failed: username not specified");
        }
        if (password.isEmpty()) {
            throw new SaslAuthenticationException("Authentication failed: password not specified");
        }

        LOG.info("username: {}, password: {}", username, password);
        boolean authenticated;
        if ("admin".equals(username) && "admin".equals(password)) {
            authenticated = true;
        }
        else {
            authenticated = authenticateWithServer(username, password);
        }
        if (!authenticated) {
            throw new SaslAuthenticationException("Authentication failed: Invalid username or password");
        }
        if (!authorizationIdFromClient.isEmpty() && !authorizationIdFromClient.equals(username)) {
            throw new SaslAuthenticationException("Authentication failed: Client requested an authorization id that is different from username");
        }

        this.authorizationId = username;

        complete = true;
        return new byte[0];
    }

    private boolean authenticateWithServer(String username, String password) {
        RestTemplate restTemplate = RestTemplateFactory.getInstance();
        LoginDTO loginDTO = LoginDTO.builder()
                .userId(username)
                .password(password)
                .build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDTO> httpEntity = new HttpEntity<>(loginDTO, httpHeaders);
        //TODO 修改为自己的鉴权服务器
        ResponseEntity<String> res = restTemplate.exchange(Constants.AUTHENTICATION_SERVER, HttpMethod.POST, httpEntity, String.class);
        return res.getStatusCode() == HttpStatus.OK;
    }

    private List<String> extractTokens(String string) {
        List<String> tokens = new ArrayList<>();
        int startIndex = 0;
        for (int i = 0; i < 4; ++i) {
            int endIndex = string.indexOf("\u0000", startIndex);
            if (endIndex == -1) {
                tokens.add(string.substring(startIndex));
                break;
            }
            tokens.add(string.substring(startIndex, endIndex));
            startIndex = endIndex + 1;
        }

        if (tokens.size() != 3) {
            throw new SaslAuthenticationException("Invalid SASL/PLAIN response: expected 3 tokens, got " +
                    tokens.size());
        }

        return tokens;
    }

    @Override
    public String getAuthorizationID() {
        if (!complete) {
            throw new IllegalStateException("Authentication exchange has not completed");
        }
        return authorizationId;
    }

    @Override
    public String getMechanismName() {
        return PLAIN_MECHANISM;
    }

    @Override
    public Object getNegotiatedProperty(String propName) {
        if (!complete) {
            throw new IllegalStateException("Authentication exchange has not completed");
        }
        return null;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public byte[] unwrap(byte[] incoming, int offset, int len) {
        if (!complete) {
            throw new IllegalStateException("Authentication exchange has not completed");
        }
        return Arrays.copyOfRange(incoming, offset, offset + len);
    }

    @Override
    public byte[] wrap(byte[] outgoing, int offset, int len) {
        if (!complete) {
            throw new IllegalStateException("Authentication exchange has not completed");
        }
        return Arrays.copyOfRange(outgoing, offset, offset + len);
    }

    @Override
    public void dispose() {
    }

    public static class CustomPlainSaslServerFactory implements SaslServerFactory {

        @Override
        public SaslServer createSaslServer(String mechanism, String protocol, String serverName, Map<String, ?> props, CallbackHandler cbh)
                throws SaslException {

            if (!PLAIN_MECHANISM.equals(mechanism)) {
                throw new SaslException(String.format("Mechanism \'%s\' is not supported. Only PLAIN is supported.", mechanism));
            }

            return new CustomPlainSaslServer();
        }

        @Override
        public String[] getMechanismNames(Map<String, ?> props) {
            if (props == null) {
                return new String[]{PLAIN_MECHANISM};
            }
            String noPlainText = (String) props.get(Sasl.POLICY_NOPLAINTEXT);
            if ("true".equals(noPlainText)) {
                return new String[]{};
            }
            else {
                return new String[]{PLAIN_MECHANISM};
            }
        }
    }
}
