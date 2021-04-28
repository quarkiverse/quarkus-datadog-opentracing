package io.quarkiverse.opentracing.datadog.util;

import java.util.Properties;
import java.util.stream.StreamSupport;

import org.eclipse.microprofile.config.Config;

public interface ConfigAdapter {
    static final String DATADOG_ENV_PREFIX = "DD_";
    static final String DATADOG_PREFIX = "dd.";
    static final String QUARKUS_DATADOG_PREFIX = "quarkus.datadog.";
    static final String ENABLED = QUARKUS_DATADOG_PREFIX + "enabled";
    static final String MDC_INJECTION_ENABLED = QUARKUS_DATADOG_PREFIX + "logs.injection";

    static String formatEnvironmentVariableAsPropertyName(final String env) {
        return env.replaceAll("_", ".")
                .toLowerCase()
                .replaceAll(DATADOG_PREFIX, "");
    }

    /**
     * Capture Datadog properties from the configuration.
     * 
     * Configuration coming from dd.anything has priority over DatadogOtRuntimeConfig. This is to
     * support official Datadog environment variables.
     * 
     * @param config Quarkus global configuration
     * @return Datadog configuration as {@link Properties}
     */
    static Properties captureProperties(Config config) {

        Properties properties = new Properties();

        //@formatter:off
        StreamSupport.stream(config.getPropertyNames()
            .spliterator(), false)
            .forEach(name -> {

                if (name.startsWith(DATADOG_ENV_PREFIX)) {

                    // Environment variable: override value if already set
                    config
                        .getOptionalValue(name, String.class)
                        .ifPresent(value -> 
                            properties.put(formatEnvironmentVariableAsPropertyName(name), value));
                } 
                
                else if (name.startsWith(DATADOG_PREFIX)) {

                    // Quarkus property: map to Datadog format and put if absent
                    config
                        .getOptionalValue(name, String.class)
                        .ifPresent(value -> 
                            properties.putIfAbsent(name.replaceAll(DATADOG_PREFIX, ""), value));
                }
            });
        //@formatter:on

        return properties;
    }
}
