package com.github.t1.jsonbap.impl;

import com.github.t1.exap.insight.Method;
import com.github.t1.exap.insight.Type;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.stream.Stream;

@RequiredArgsConstructor
class GetterProperty implements Property {
    static Stream<GetterProperty> getterProperties(Type type) {
        return type.getAllMethods().stream()
                .filter(GetterProperty::isGetter)
                .map(GetterProperty::of);
    }

    private static boolean isGetter(Method method) {
        return method.isPublic()
               && method.getParameters().isEmpty()
               && method.getName().length() > 3
               && method.getName().startsWith("get") // TODO booleans may start with `is`
               && Character.isUpperCase(method.getName().charAt(3))
               && !"java.lang.Object".equals(method.getDeclaringType().getFullName())
               && !"void".equals(method.getReturnType().getFullName());
    }

    private static GetterProperty of(Method method) {
        return of(method.getReturnType().getFullName(), nameFrom(method.getName()), "object." + method.getName() + "()");
    }

    private static GetterProperty of(String typeName, String name, String valueExpression) {
        return new GetterProperty(typeName, name, valueExpression);
    }

    private static String nameFrom(String name) {
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }

    private final String typeName;
    @Getter @Accessors(fluent = true)
    private final String name;
    private final String valueExpression;

    @Override public String toString() {return "getter:" + name + "->" + typeName;}

    @Override public void write(StringBuilder out) {
        if (PRIMITIVE_TYPES.contains(typeName)) {
            out.append("        out.write(\"").append(name()).append("\", ")
                    .append(valueExpression).append(");\n");
        } else {
            out.append("        context.serialize(\"").append(name()).append("\", ")
                    .append(valueExpression).append(", out);\n");
        }
    }
}
