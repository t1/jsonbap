package com.github.t1.jsonbap.impl;

import com.github.t1.exap.insight.Method;
import com.github.t1.exap.insight.Type;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.stream.Stream;

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

    static Stream<GetterProperty> getterProperties(Type type) {
        return type.getAllMethods().stream()
                .filter(GetterProperty::isGetter)
                .map(GetterProperty::of)
                .sorted();
    }

    @Override public void write(StringBuilder out) {
        if (PRIMITIVE_TYPES.contains(typeName)) {
            out.append("        out.write(\"").append(name()).append("\", ")
                    .append(valueExpression).append(");\n");
        } else {
            out.append("        context.serialize(\"").append(name()).append("\", ")
                    .append(valueExpression).append(", out);\n");
        }
    }

    /**
     * The primitive types that JsonGenerator supports directly; no null-check required.
     * All other types are serialized via the context, which does the null-check.
     */
    private static final Set<String> PRIMITIVE_TYPES = Set.of(
            "int",
            "long",
            "double",
            "boolean");
}
