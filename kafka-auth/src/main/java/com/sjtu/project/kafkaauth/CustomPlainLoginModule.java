package com.sjtu.project.kafkaauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.util.Map;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/26 13:13
 */
public class CustomPlainLoginModule implements LoginModule {

    private static final Logger LOG = LoggerFactory.getLogger(CustomPlainLoginModule.class);

    private static final String USERNAME_CONFIG = "username";

    private static final String PASSWORD_CONFIG = "password";

    private static final String AUTHENTICATION_SERVER_CONFIG = "authentication_server";

    static {
        CustomPlainSaslServerProvider.initialize();
    }

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        String username = (String) options.get(USERNAME_CONFIG);
        if (username != null) {
            subject.getPublicCredentials().add(username);
        }
        String password = (String) options.get(PASSWORD_CONFIG);
        if (password != null) {
            subject.getPrivateCredentials().add(password);
        }
        String authenticationServer = (String) options.get(AUTHENTICATION_SERVER_CONFIG);
        Constants.AUTHENTICATION_SERVER = authenticationServer;
        LOG.info("Authentication_Server: {}", authenticationServer);
    }

    @Override
    public boolean login() throws LoginException {
        return true;
    }

    @Override
    public boolean commit() throws LoginException {
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        return true;
    }
}
