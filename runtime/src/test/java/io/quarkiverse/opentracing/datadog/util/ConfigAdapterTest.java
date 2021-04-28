package io.quarkiverse.opentracing.datadog.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

import org.eclipse.microprofile.config.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ConfigAdapterTest {
    static final String PROPERTY_NAME = "test.prop";
    static final String ENVIRONMENT_VARIABLE_NAME = ConfigAdapter.DATADOG_ENV_PREFIX + PROPERTY_NAME.replaceAll("\\.", "_")
            .toUpperCase();
    static final String PROPERTY_VARIABLE_NAME = ConfigAdapter.DATADOG_PREFIX + PROPERTY_NAME;

    @Test
    public void datadogPropertyNotFoundWhenNoConfiguration() {

        final Config mockedConfig = Mockito.mock(Config.class);
        Mockito.when(mockedConfig.getPropertyNames())
                .thenReturn(new ArrayList<String>());

        final Properties properties = ConfigAdapter.captureProperties(mockedConfig);

        Assertions.assertNull(properties.get(PROPERTY_NAME));
    }

    @Test
    public void datadogPropertyNotFoundWhenValueIsNotSet() {

        // given
        final Config mockedConfig = Mockito.mock(Config.class);
        Mockito.when(mockedConfig.getPropertyNames())
                .thenReturn(Arrays.asList(PROPERTY_VARIABLE_NAME));
        Mockito.when(mockedConfig.getOptionalValue(PROPERTY_VARIABLE_NAME, String.class))
                .thenReturn(Optional.empty());

        // when
        final Properties properties = ConfigAdapter.captureProperties(mockedConfig);

        // then
        Assertions.assertNull(properties.get(PROPERTY_NAME));
    }

    @Test
    public void datadogPropertyFoundWhenProvidedThroughPropertyConfiguration() {

        // given
        final String value = "test";
        final Config mockedConfig = Mockito.mock(Config.class);
        Mockito.when(mockedConfig.getPropertyNames())
                .thenReturn(Arrays.asList(PROPERTY_VARIABLE_NAME));
        Mockito.when(mockedConfig.getOptionalValue(PROPERTY_VARIABLE_NAME, String.class))
                .thenReturn(Optional.of(value));

        // when
        final Properties properties = ConfigAdapter.captureProperties(mockedConfig);

        // then
        Assertions.assertEquals(properties.get(PROPERTY_NAME), value);
    }

    @Test
    public void environmentValueShouldOverridePropertyConfigurationValue() {

        final String quarkusValue = "from properties";
        final String environmentValue = "from environment";

        // given
        final Config mockedConfig = Mockito.mock(Config.class);
        Mockito.when(mockedConfig.getPropertyNames())
                .thenReturn(Arrays.asList(PROPERTY_VARIABLE_NAME, ENVIRONMENT_VARIABLE_NAME));
        Mockito.when(mockedConfig.getOptionalValue(PROPERTY_VARIABLE_NAME, String.class))
                .thenReturn(Optional.of(quarkusValue));
        Mockito.when(mockedConfig.getOptionalValue(ENVIRONMENT_VARIABLE_NAME, String.class))
                .thenReturn(Optional.of(environmentValue));

        // when
        final Properties properties = ConfigAdapter.captureProperties(mockedConfig);

        // then
        Assertions.assertEquals(environmentValue, properties.get(PROPERTY_NAME));
    }
}
