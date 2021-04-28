package io.quarkiverse.opentracing.datadog.test;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import datadog.opentracing.DDTracer;
import datadog.trace.api.GlobalTracer;
import io.quarkus.test.QuarkusUnitTest;


/**
 * Should not have any tracer registered when the extension is disabled
 */
public class DatadogOpentracingDeactivationTest
{

    @RegisterExtension
    static final QuarkusUnitTest unitTest =
            new QuarkusUnitTest().setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class))
                    .overrideConfigKey("quarkus.datadog.enabled", "false");

    @Test
    public void testNoDatadogTracer() {

        // Should be the default NO_OP internal tracer class instead
        Assertions.assertFalse(GlobalTracer.get() instanceof DDTracer);
    }
}
