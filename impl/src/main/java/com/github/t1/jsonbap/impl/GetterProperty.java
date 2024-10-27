package com.github.t1.jsonbap.impl;

import com.github.t1.exap.insight.ElementalAnnotations;
import com.github.t1.exap.insight.Method;
import com.github.t1.exap.insight.Type;
import lombok.NonNull;

import java.util.stream.Stream;

class GetterProperty extends Property<Method> {
    static Stream<Property<Method>> getterProperties(JsonbapConfig jsonbapConfig, TypeConfig typeConfig, Type type) {
        return type.getAllMethods().stream()
                .filter(GetterProperty::isGetter)
                .map(method -> new GetterProperty(jsonbapConfig, typeConfig, method, method.annotations()));
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

    private GetterProperty(JsonbapConfig jsonbapConfig, TypeConfig typeConfig, Method getter, ElementalAnnotations annotations) {
        super(jsonbapConfig, typeConfig, getter, annotations);
    }

    @Override protected Property<?> withAnnotations(@NonNull ElementalAnnotations annotations) {
        return new GetterProperty(this.jsonbapConfig, this.typeConfig, this.elemental, annotations);
    }

    @Override protected String propertyType() {return "getter";}

    @Override protected String rawName() {
        var name = elemental.name();
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends Property<?>> Either<T, String> or(T that) {
        return Either.<T, String>value((T) this).with(Property::isPublic, that);
    }

    @Override protected String typeName() {return elemental().getReturnType().getFullName();}

    @Override protected String valueExpression() {return "object." + elemental.name() + "()";}
}
