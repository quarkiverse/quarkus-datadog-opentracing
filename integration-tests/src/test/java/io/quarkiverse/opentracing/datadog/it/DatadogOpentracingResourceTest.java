package io.quarkiverse.opentracing.datadog.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class DatadogOpentracingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/datadog-opentracing")
                .then()
                .statusCode(200)
                .body(is("Hello datadog-opentracing"));
    }
}
