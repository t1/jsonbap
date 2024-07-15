package com.github.t1.jsonbap.impl;

import com.github.t1.exap.reflection.Method;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

@RequiredArgsConstructor
class GetterProperty implements Property {
    static boolean isGetter(Method method) {
        return method.isPublic()
               && method.getName().startsWith("get") // TODO booleans may start with `is`
               && !"java.lang.Object".equals(method.getDeclaringType().getFullName());
    }

    static GetterProperty of(Method method) {
        return of(method.getReturnType().getFullName(), nameFrom(method.getName()), "object." + method.getName() + "()");
    }

    static GetterProperty of(String typeName, String name, String valueExpression) {
        return new GetterProperty(typeName, name, valueExpression);
    }

    private static String nameFrom(String name) {
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }

    private final String typeName;
    @Getter @Accessors(fluent = true)
    private final String name;
    protected final String valueExpression;

    @Override public void write(StringBuilder out) {
        if (isPrimitive()) {
            out.append("        out.write(\"").append(name()).append("\", ")
                    .append(valueExpression).append(");\n");
        } else {
            out.append("        context.serialize(\"").append(name()).append("\", ")
                    .append(valueExpression).append(", out);\n");
        }
    }

    /** The primitive types that JsonGenerator supports directly */
    private boolean isPrimitive() {
        return Set.of("int", "long", "double", "boolean").contains(typeName);
    }
}
