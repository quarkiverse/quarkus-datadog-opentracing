package io.quarkiverse.opentracing.datadog.graal;

import com.oracle.svm.core.annotate.Alias;
import com.oracle.svm.core.annotate.RecomputeFieldValue;
import com.oracle.svm.core.annotate.TargetClass;

@TargetClass(className = "org.jctools.util.UnsafeLongArrayAccess")
public final class Target_org_jctools_util_UnsafeLongArrayAccess {
    @Alias
    @RecomputeFieldValue(kind = RecomputeFieldValue.Kind.ArrayIndexShift, declClass = long[].class)
    public static int LONG_ELEMENT_SHIFT;
}
