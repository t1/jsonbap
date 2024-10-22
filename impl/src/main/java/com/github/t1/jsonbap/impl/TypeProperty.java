package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.AnnotationWrapper;
import com.github.t1.exap.insight.Elemental;
import com.github.t1.exap.insight.ElementalAnnotations;
import com.github.t1.exap.insight.Type;
import lombok.NonNull;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.stream.Stream;

/// the `type` property for polymorphic (de)serialization; by default named `@type`.
///
/// @see jakarta.json.bind.annotation.JsonbTypeInfo
class TypeProperty extends Property<Type> {
    static Stream<Property<Type>> typeProperties(TypeConfig config, Type type) {
        return typeInfo(type).map(typeInfo -> new TypeProperty(config, type, type.annotations(), typeInfo));
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

    public TypeProperty(TypeConfig config, Type type, ElementalAnnotations annotations, AnnotationWrapper typeInfo) {
        super(config, type, annotations);
        this.typeInfo = typeInfo;
    }

    @Override protected Property<?> withAnnotations(@NonNull ElementalAnnotations annotations) {
        return new TypeProperty(this.config, this.elemental, annotations, this.typeInfo);
    }

    @Override public String toString() {return "type " + elemental.name();}

    @Override public String name() {return typeInfo.getStringProperty("key");}

    @Override protected <T extends Property<?>> Either<T, String> or(T that) {
        return Either.or("the type property " + name() + " specified via the `@JsonbTypeInfo` annotation " +
                         "must not also be defined as a field or getter");
    }

    @Override protected void writeTo(TypeGenerator typeGenerator, StringBuilder out) {
        var key = typeInfo.getStringProperty("key");
        if (key == null) key = "@type";
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
        out.append("        out.write(")
                .append("\"").append(key).append("\", ")
                .append("\"").append(alias).append("\");\n");
    }
}
