package io.quarkiverse.opentracing.datadog;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import datadog.opentracing.DDTracer;
import io.opentracing.util.GlobalTracer;
import io.quarkiverse.opentracing.datadog.logging.MdcInjectionScopeListener;
import io.quarkiverse.opentracing.datadog.util.ConfigAdapter;
import io.quarkus.runtime.annotations.Recorder;

@Recorder
public class DatadogTracerRecorder {
    public void installDatadogTracer() {

        final Config config = ConfigProvider.getConfig();

        // Create the tracer from a property object
        DDTracer tracer = DDTracer.builder()
                .withProperties(ConfigAdapter.captureProperties(config))
                .build();

        // Activate MDC injection only if enabled from the configuration
        if (config.getValue(ConfigAdapter.MDC_INJECTION_ENABLED, Boolean.class)) {

            tracer.addScopeListener(new MdcInjectionScopeListener());
        }

        // Register the tracer
        GlobalTracer.registerIfAbsent(tracer);
        datadog.trace.api.GlobalTracer.registerIfAbsent(tracer);
    }
}
