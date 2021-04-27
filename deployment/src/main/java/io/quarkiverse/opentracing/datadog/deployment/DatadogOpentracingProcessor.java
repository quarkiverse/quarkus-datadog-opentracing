package io.quarkiverse.opentracing.datadog.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class DatadogOpentracingProcessor {

    private static final String FEATURE = "datadog-opentracing";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }
}
