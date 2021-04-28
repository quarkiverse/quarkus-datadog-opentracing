# Quarkus - Datadog Opentracing
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-1-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

## About this extension

This repository hosts an extension to support and activate the Datadog opentracing tracer.

Both JVM and Native modes are supported.

This extension is not providing any opentracing capability feature. For that, please please add the **quarkus-smallrye-opentracing** extension and any other additional 3rd party instrumentation library you would need to cover sub systems not already covered by smallrye-opentracing.

This extension is for you if:
- You are using Quarkus or is interested in migrating to Quarkus
- You don't want to use Datadog's java agent for tracing capabilities
- You want to have Datadog tracing capabilities on your application running in Native.


**Future support of this extension** 

The support for this extension may stop as soon as Datadog provides a full support of the OpenTelemetry Collector, especially (at the date of this README):
- the support of generic JVM metrics as non custom metrics when coming from OpenTelemetry
- the support of log collection & forwarding on the Datadog OpenTelemetry exporter (to avoid to have to install and run both Datadog & OpenTelemetry agents on your infrastructure)


## How to setup the extension

A complete example on how to setup your project for this extension is [available here](https://github.com/nicolas-vivot/quarkiverse-datadog-opentracing-setup-example)


Long story short, you will need to include different dependencies depending on what you want to achieve.

### Your project dependencies

As explained above, this extension only provides the opentracing Datadog's tracer implementation.

- to activate opentracing capabilities, please add the **quarkus-smallrye-opentracing** extension.
- to have logs correlation, please add the **quarkus-logging-json** extension. 

Note: Since Datadog libraries depends on opentracing 0.32.0 and Quarkus is still on 0.31.0, you will have to override the version on your project as well.

**Example**

```
    <!-- Required: to activate tracing capabilities on your application -->
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-smallrye-opentracing</artifactId>
    </dependency>
    
    <!-- Required: This extension to activate Datadog's tracer -->
    <dependency>
      <groupId>io.quarkiverse.opentracing.datadog</groupId>
      <artifactId>quarkus-datadog-opentracing</artifactId>
      <version>${replace-by-your.version}</version>
    </dependency>
    
    <!-- Optional: Only if you activate logs correlation feature -->
    <dependency>
      <groupId>io.quarkiverse.loggingjson</groupId>
      <artifactId>quarkus-logging-json</artifactId>
      <version>${replace-by-your.version}</version>
    </dependency>
    
    <!-- Required: to override version 0.31.0 from Quarkus. 
         Datadog libraries are using 0.32.0 -->
    <dependency>
      <groupId>io.opentracing</groupId>
      <artifactId>opentracing-api</artifactId>
      <version>0.32.0</version>
    </dependency>
    <dependency>
      <groupId>io.opentracing</groupId>
      <artifactId>opentracing-util</artifactId>
      <version>0.32.0</version>
    </dependency>
    
    <!-- Optional : only if you want to use an upgraded version of Datadog libraries -->
    <dependency>
      <groupId>com.datadoghq</groupId>
      <artifactId>dd-trace-api</artifactId>
      <version>${replace-by-your.version}</version>
    </dependency>

    <dependency>
      <groupId>com.datadoghq</groupId>
      <artifactId>dd-trace-ot</artifactId>
      <version>${replace-by-your.version}</version>
    </dependency>
    
```


## Configuration

### Extension configuration


This extension is enabled by default. The logs injection feature is disabled by default.

To activate both:

```
quarkus.datadog.enabled=true
quarkus.datadog.logs.injection=true
```

### Datadog tracer configuration

All official datadog tracer's properties are supported as long as the namings are correct according to the [official documentation](https://docs.datadoghq.com/tracing/setup_overview/setup/java/?tab=containers).
Properties should be configured under *dd* root property.

Configuration example:

```
dd.logs.injection=true
dd.agent.host=some-agent-hostname
dd.trace.agent.port=1234
```

**Environment variable support**

Instead of configuring the tracer from your application properties, you can also setup that configuration from environment variables.
The extension would discover all environment variables starting with the prefix **DD_** and map it to their corresponding properties (i.e. dd.some.property)

**Be careful, an environment variable overrides its property value**


## Datadog libraries support matrix

This extension support / has been tested with

Type | Library | Version
------|---------|--------
Support | dd-trace-api | 0.74.0 to 0.78.3
Support | dd-trace-ot | 0.74.0 to 0.78.3


## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="https://github.com/nicolas-vivot"><img src="https://avatars.githubusercontent.com/u/79290619?v=4?s=100" width="100px;" alt=""/><br /><sub><b>nvivot</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-datadog-opentracing/commits?author=nicolas-vivot" title="Code">💻</a> <a href="#maintenance-nicolas-vivot" title="Maintenance">🚧</a></td>
  </tr>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!