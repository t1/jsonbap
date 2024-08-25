package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.Method;
import com.github.t1.exap.insight.Type;

import java.util.stream.Stream;

class GetterProperty extends Property {
    static Stream<Property> getterProperties(Type type) {
        return type.getAllMethods().stream()
                .filter(GetterProperty::isGetter)
                .map(GetterProperty::new);
    }

    private static boolean isGetter(Method method) {
        return method.isPublic()
               && method.getParameters().isEmpty()
               && method.name().length() > 3
               && method.name().startsWith("get") // TODO booleans may start with `is`
               && Character.isUpperCase(method.name().charAt(3))
               && !"java.lang.Object".equals(method.getDeclaringType().getFullName())
               && !"void".equals(method.getReturnType().getFullName());
    }

    public GetterProperty(Method getter) {super(nameFrom(getter.name()), getter, getter.annotations());}

    private static String nameFrom(String name) {
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }

    @Override protected void writeTo(TypeGenerator typeGenerator, StringBuilder out) {
        var getter = (Method) elemental();
        var typeName = getter.getReturnType().getFullName();
        var valueExpression = "object." + getter.name() + "()";
        if (PRIMITIVE_TYPES.contains(typeName)) {
            out.append("        out.write(\"").append(name()).append("\", ")
                    .append(valueExpression).append(");\n");
        } else {
            out.append("        context.serialize(\"").append(name()).append("\", ")
                    .append(valueExpression).append(", out);\n");
        }
    }
}
