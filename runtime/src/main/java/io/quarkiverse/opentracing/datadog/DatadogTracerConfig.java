package io.quarkiverse.opentracing.datadog;

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
    }

}
