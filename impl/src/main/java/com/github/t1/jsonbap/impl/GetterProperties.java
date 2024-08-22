package com.github.t1.jsonbap.impl;

import com.github.t1.exap.insight.Method;
import com.github.t1.exap.insight.Type;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.github.t1.jsonbap.impl.Property.PRIMITIVE_TYPES;

class GetterProperties {
    static Stream<Property> getterProperties(Type type) {
        return type.getAllMethods().stream()
                .filter(GetterProperties::isGetter)
                .map(GetterProperties::of);
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

    private static Property of(Method method) {
        return of(method.getReturnType().getFullName(), nameFrom(method.getName()), "object." + method.getName() + "()");
    }

    private static Property of(String typeName, String name, String valueExpression) {
        return new Property(name, new GetterPropertyWriter(typeName, name, valueExpression));
    }

    private static String nameFrom(String name) {
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }

    private record GetterPropertyWriter(
            String typeName,
            String name,
            String valueExpression
    ) implements Consumer<StringBuilder> {

        @Override public String toString() {return "getter:" + name + "->" + typeName;}

        @Override public void accept(StringBuilder out) {
            if (PRIMITIVE_TYPES.contains(typeName)) {
                out.append("        out.write(\"").append(name()).append("\", ")
                        .append(valueExpression).append(");\n");
            } else {
                out.append("        context.serialize(\"").append(name()).append("\", ")
                        .append(valueExpression).append(", out);\n");
            }
        }
    }
}
