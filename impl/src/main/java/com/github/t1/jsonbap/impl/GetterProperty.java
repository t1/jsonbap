package com.github.t1.jsonbap.impl;

import com.github.t1.exap.insight.Method;
import com.github.t1.exap.insight.Type;
import com.github.t1.jsonbap.runtime.FluentParser;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.stream.Stream;

@Getter @Accessors(fluent = true)
class GetterProperty extends Property<Method> {
    static Stream<Property<Method>> getterProperties(JsonbapConfig jsonbapConfig, TypeConfig typeConfig, Type type) {
        return type.getAllMethods().stream()
                .filter(GetterProperty::isGetter)
                .map(method -> new GetterProperty(jsonbapConfig, typeConfig, method, rawName(method)));
    }

    private static boolean isGetter(Method method) {
        return method.isPublic()
               && method.getParameters().isEmpty()
               && !"java.lang.Object".equals(method.getDeclaringType().getFullName())
               && !"void".equals(method.getReturnType().getFullName())
               && (isNormalGetter(method) || isBooleanGetterWithIs(method));
    }

    private static boolean isNormalGetter(Method method) {
        return method.name().length() > 3
               && method.name().startsWith("get")
               && Character.isUpperCase(method.name().charAt(3));
    }

    private static boolean isBooleanGetterWithIs(Method method) {
        return "boolean".equals(method.getReturnType().getFullName())
               && method.name().length() > 2
               && method.name().startsWith("is")
               && Character.isUpperCase(method.name().charAt(2));
    }

    private static String rawName(Method method) {
        var offset = isNormalGetter(method) ? 3 : 2;
        var name = method.name();
        return Character.toLowerCase(name.charAt(offset)) + name.substring(offset + 1);
    }

    private final String rawName;

    private GetterProperty(JsonbapConfig jsonbapConfig, TypeConfig typeConfig, Method getter, String rawName) {
        super(jsonbapConfig, typeConfig, getter);
        this.rawName = rawName;
    }

    @Override protected String propertyType() {return "getter";}

    @Override boolean isSettable() {
        return elemental().getDeclaringType().hasMethod("set" + FluentParser.titleCase(rawName()));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends Property<?>> Either<T, String> or(T that) {
        return Either.<T, String>value((T) this).with(Property::isPublic, that);
    }

    @Override protected Type type() {return elemental().getReturnType();}

    @Override protected String readExpression() {return "object." + elemental.name() + "()";}

    @Override
    protected String writeExpression(String value) {return "set" + FluentParser.titleCase(rawName()) + "(" + value + ")";}
}
