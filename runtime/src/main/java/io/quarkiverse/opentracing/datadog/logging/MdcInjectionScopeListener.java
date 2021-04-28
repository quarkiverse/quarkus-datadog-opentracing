package io.quarkiverse.opentracing.datadog.logging;

import org.jboss.logging.MDC;

import datadog.trace.api.CorrelationIdentifier;
import datadog.trace.context.ScopeListener;

public class MdcInjectionScopeListener implements ScopeListener {

    @Override
    public void afterScopeActivated() {
        MDC.put(CorrelationIdentifier.getTraceIdKey(), CorrelationIdentifier.getTraceId());
        MDC.put(CorrelationIdentifier.getSpanIdKey(), CorrelationIdentifier.getSpanId());
    }

    @Override
    public void afterScopeClosed() {
        MDC.remove(CorrelationIdentifier.getTraceIdKey());
        MDC.remove(CorrelationIdentifier.getSpanIdKey());
    }
}
