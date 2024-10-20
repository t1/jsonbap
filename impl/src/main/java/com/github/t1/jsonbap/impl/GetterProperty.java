package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.Method;
import com.github.t1.exap.insight.Type;

import java.util.stream.Stream;

class GetterProperty extends Property<Method> {
    static Stream<Property<Method>> getterProperties(TypeConfig config, Type type) {
        return type.getAllMethods().stream()
                .filter(GetterProperty::isGetter)
                .map(method -> new GetterProperty(config, method));
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

    private GetterProperty(TypeConfig config, Method getter) {super(config, getter);}

    @Override public String toString() {return "getter " + elemental.name();}

    @Override protected String rawName() {
        var name = elemental.name();
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }

    @Override protected void writeTo(TypeGenerator typeGenerator, StringBuilder out) {
        write(
                elemental().getReturnType().getFullName(),
                "object." + elemental.name() + "()",
                out);
    }
}
