package com.github.t1.jsonbap.impl;

import com.github.t1.exap.insight.AnnotationWrapper;
import com.github.t1.exap.insight.Elemental;
import com.github.t1.exap.insight.Type;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.stream.Stream;

/// the `type` property for polymorphic (de)serialization; by default named `@type`.
///
/// @see jakarta.json.bind.annotation.JsonbTypeInfo
class TypeProperty extends Property<Type> {
    static Stream<Property<Type>> typeProperties(JsonbapConfig jsonbapConfig, TypeConfig typeConfig, Type type) {
        return typeInfo(type).map(typeInfo -> new TypeProperty(jsonbapConfig, typeConfig, type, typeInfo));
    }

    private static Stream<AnnotationWrapper> typeInfo(Type type) {
        return type.allInterfaces()
                .flatMap(Elemental::annotationWrappers)
                .filter(TypeProperty::isJsonbTypeInfo);
    }

    private static boolean isJsonbTypeInfo(AnnotationWrapper annotationWrapper) {
        return annotationWrapper.getAnnotationType().getFullName().equals("jakarta.json.bind.annotation.JsonbTypeInfo");
    }


    private final AnnotationWrapper typeInfo;

    public TypeProperty(JsonbapConfig jsonbapConfig, TypeConfig typeConfig, Type type, AnnotationWrapper typeInfo) {
        super(jsonbapConfig, typeConfig, type);
        this.typeInfo = typeInfo;
    }

    @Override protected String propertyType() {return "type";}

    @Override public String name() {
        var key = typeInfo.getStringProperty("key");
        return (key == null) ? "@type" : key;
    }

    @Override protected <T extends Property<?>> Either<T, String> or(T that) {
        return Either.or("the type property " + name() + " specified via the `@JsonbTypeInfo` annotation " +
                         "must not also be defined as a field or getter");
    }

    @Override protected String typeName() {return "String";}

    @Override protected String valueExpression() {
        var list = typeInfo.getAnnotationProperties("value");
        var alias = list.stream()
                .filter(value -> {
                    var type = value.getProperty("type", DeclaredType.class);
                    var typeName = ((TypeElement) type.asElement()).getQualifiedName().toString();
                    return typeName.equals(elemental().getFullName());
                })
                .findAny()
                .map(value -> value.getStringProperty("alias"))
                .orElseThrow();
        return '"' + alias + '"';
    }
}
