package com.github.t1.jsonbap.impl;

import com.github.t1.exap.reflection.Method;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
class GetterProperty implements Property {
    static boolean isGetter(Method method) {
        return method.isPublic()
               && method.getName().startsWith("get") // TODO booleans may start with `is`
               && !"java.lang.Object".equals(method.getDeclaringType().getFullName());
    }

    static GetterProperty of(Method method) {
        return of(nameFrom(method.getName()), "object." + method.getName() + "()");
    }

    static GetterProperty of(String name, String valueExpression) {
        return new GetterProperty(name, valueExpression);
    }

    private static String nameFrom(String name) {
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }

    @Getter @Accessors(fluent = true)
    private final String name;
    protected final String valueExpression;

    @Override public void write(StringBuilder out) {
        out.append("        context.serialize(\"").append(name()).append("\", ")
                .append(valueExpression).append(", out);\n");
    }
}
