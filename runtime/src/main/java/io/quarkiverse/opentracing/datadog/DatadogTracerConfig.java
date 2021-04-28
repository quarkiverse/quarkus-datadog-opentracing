package io.quarkiverse.opentracing.datadog;

import java.util.Map;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

public class DatadogTracerConfig {

    @ConfigRoot(name = "datadog", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
    public static class DatadogOtBuildConfig {

        /**
         * Datadog tracer support
         * <p>
         * Datadog tracer support is enabled by default.
         */
        @ConfigItem(defaultValue = "true")
        public boolean enabled;

        /**
         * MDC injection support. Injects Datadog trace_id and span_id into MDC for correlation
         * between traces and logs sent to Datadog
         * <p>
         * MDC injection is enabled by default
         */
        @ConfigItem(defaultValue = "false", name = "logs.injection")
        public boolean logInjection;
    }

    /**
     * Runtime configuration for dd-trace-java ot
     */
    @ConfigRoot(name = "datadog", phase = ConfigPhase.RUN_TIME)
    public static class DatadogOtRuntimeConfig {

        /**
         * Datadog tracer configuration properties
         * <p>
         * A property source for the configuration of the Datadog tracer implementation
         * (dd-trace-java ot library) to trace your application.
         * <p>
         * Available values: all values found from official datadog documentation matching the
         * supported version.
         */
        @ConfigItem(name = ConfigItem.PARENT)
        Map<String, String> datadog;
    }

}
