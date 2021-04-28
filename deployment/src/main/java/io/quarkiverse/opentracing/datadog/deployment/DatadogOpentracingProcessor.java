package io.quarkiverse.opentracing.datadog.deployment;

import java.util.function.BooleanSupplier;
import io.quarkiverse.opentracing.datadog.DatadogTracerConfig;
import io.quarkiverse.opentracing.datadog.DatadogTracerRecorder;
import io.quarkus.deployment.Capability;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.CapabilityBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;

/**
 * Add support for Datadog tracer implementation.
 */
class DatadogOpentracingProcessor
{

    private static final String FEATURE = "datadog-opentracing";

    public static class DatadogTracerEnabled implements BooleanSupplier
    {

        DatadogTracerConfig.DatadogOtBuildConfig buildConfig;

        @Override
        public boolean getAsBoolean() {
            return buildConfig.enabled;
        }
    }

    @BuildStep(onlyIf = DatadogTracerEnabled.class)
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep(onlyIf = DatadogTracerEnabled.class)
    CapabilityBuildItem capability() {
        return new CapabilityBuildItem(Capability.OPENTRACING);
    }

    @BuildStep(onlyIf = DatadogTracerEnabled.class)
    @Record(ExecutionTime.RUNTIME_INIT)
    void installDatadogTracer(DatadogTracerRecorder recorder) {
        recorder.installDatadogTracer();
    }
}
