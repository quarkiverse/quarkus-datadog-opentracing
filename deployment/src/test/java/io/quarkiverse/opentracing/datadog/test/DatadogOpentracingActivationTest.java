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
 * Should have a {@link DDTracer} bean registered when the extension is enabled
 */
public class DatadogOpentracingActivationTest
{

    @RegisterExtension
    static final QuarkusUnitTest unitTest =
            new QuarkusUnitTest().setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class));

    @Test
    public void testDatadogTracerAvailable() {

        Assertions.assertTrue(GlobalTracer.get() instanceof DDTracer);
    }
}
