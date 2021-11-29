package io.quarkiverse.opentracing.datadog.deployment;

import java.util.function.BooleanSupplier;

import javax.inject.Inject;

import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;
import org.jboss.jandex.Type;

import io.quarkiverse.opentracing.datadog.DatadogTracerConfig;
import io.quarkiverse.opentracing.datadog.DatadogTracerInitializer;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveHierarchyBuildItem;

/**
 * Add support for Datadog tracer implementation.
 */
class DatadogOpentracingProcessor {

    private static final String FEATURE = "datadog-tracer";

    private static final DotName DATADOG_TRACE_PACKAGE = DotName.createSimple("datadog.trace");
    private static final String BOOLEAN_UTILITY_CLASS = "java.lang.Boolean";
    private static final String STRING_UTILITY_CLASS = "java.lang.String";

    public static class DatadogTracerEnabled implements BooleanSupplier {

        DatadogTracerConfig.DatadogOtBuildConfig buildConfig;

        @Override
        public boolean getAsBoolean() {
            return buildConfig.enabled;
        }
    }

    @Inject
    CombinedIndexBuildItem combinedIndexBuildItem;

    @BuildStep(onlyIf = DatadogTracerEnabled.class)
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep(onlyIf = DatadogTracerEnabled.class)
    void register(//
            BuildProducer<ReflectiveClassBuildItem> reflectiveClass,
            BuildProducer<ReflectiveHierarchyBuildItem> reflectiveHierarchyClass,
            BuildProducer<AdditionalBeanBuildItem> additionalBeans) {

        IndexView index = combinedIndexBuildItem.getIndex();

        index.getAllKnownSubclasses(DATADOG_TRACE_PACKAGE)
                .forEach(classInfo -> {

                    addReflectiveHierarchyClass(classInfo.asClass()
                            .name(), reflectiveHierarchyClass);

                });

        reflectiveClass.produce(new ReflectiveClassBuildItem(true, true, BOOLEAN_UTILITY_CLASS));
        reflectiveClass.produce(new ReflectiveClassBuildItem(true, true, STRING_UTILITY_CLASS));

        additionalBeans
                .produce(AdditionalBeanBuildItem.unremovableOf(DatadogTracerInitializer.class));
    }

    private void addReflectiveHierarchyClass(DotName className,
            BuildProducer<ReflectiveHierarchyBuildItem> reflectiveHierarchyClass) {
        Type jandexType = Type.create(className, Type.Kind.CLASS);
        reflectiveHierarchyClass.produce(new ReflectiveHierarchyBuildItem.Builder().type(jandexType)
                .source(getClass().getSimpleName() + " > " + jandexType.name()
                        .toString())
                .build());
    }
}
