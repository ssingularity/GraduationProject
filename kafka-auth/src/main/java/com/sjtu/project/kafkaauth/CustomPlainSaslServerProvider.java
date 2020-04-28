package com.sjtu.project.kafkaauth;

import java.security.Provider;
import java.security.Security;


/**
 * @Author: ssingualrity
 * @Date: 2020/4/26 13:17
 */
public class CustomPlainSaslServerProvider extends Provider {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("deprecation")
    protected CustomPlainSaslServerProvider() {
        super("Custom Simple SASL/PLAIN Server Provider", 1.0, "Custom Simple SASL/PLAIN Server Provider for Kafka");
        put("SaslServerFactory." + CustomPlainSaslServer.PLAIN_MECHANISM, CustomPlainSaslServer.CustomPlainSaslServerFactory.class.getName());
    }

    public static void initialize() {
        Security.addProvider(new CustomPlainSaslServerProvider());
    }
}
