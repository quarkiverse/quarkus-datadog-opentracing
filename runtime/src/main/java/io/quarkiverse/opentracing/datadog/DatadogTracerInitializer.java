package io.quarkiverse.opentracing.datadog;

import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logmanager.Logger;

import datadog.opentracing.DDTracer;
import io.opentracing.util.GlobalTracer;
import io.quarkiverse.opentracing.datadog.logging.MdcInjectionScopeListener;
import io.quarkiverse.opentracing.datadog.util.ConfigAdapter;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class DatadogTracerInitializer {
    private static final Logger LOGGER = Logger.getLogger(DatadogTracerInitializer.class.getName());

    void onStart(@Observes StartupEvent event) {

        LOGGER.fine("Initializing Datadog tracer");
        installDatadogTracer();
    }

    void installDatadogTracer() {

        final Properties properties = ConfigAdapter.captureProperties(ConfigProvider.getConfig());

        // Create the tracer from a property object
        DDTracer tracer = DDTracer.builder()
                .withProperties(properties)
                .build();

        // Activate MDC injection only if enabled from the Datadog configuration
        // Default is true in Datadog libraries, so we keep that default behavior here too
        if (Boolean.parseBoolean(//
                properties.getProperty(//
                        ConfigAdapter.MDC_INJECTION_ENABLED,
                        Boolean.TRUE.toString()))) {

            tracer.addScopeListener(new MdcInjectionScopeListener());
        }

        // Register the tracer
        GlobalTracer.registerIfAbsent(tracer);
        datadog.trace.api.GlobalTracer.registerIfAbsent(tracer);
    }
}
