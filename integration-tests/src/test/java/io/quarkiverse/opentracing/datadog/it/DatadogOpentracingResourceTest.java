package io.quarkiverse.opentracing.datadog.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class DatadogOpentracingResourceTest {

    @Test
    public void testDatadogTracer() throws InterruptedException {

        given().when()
                .get("/tracing/active")
                .then()
                .statusCode(200)
                .body(is("true"));
    }
}
