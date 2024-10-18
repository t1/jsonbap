package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.AnnotationWrapper;
import com.github.t1.exap.insight.Elemental;
import com.github.t1.exap.insight.Type;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.stream.Stream;

class TypeProperty extends Property<Type> {
    static Stream<Property<Type>> typeProperties(Type type) {
        return typeInfo(type).map(typeInfo -> new TypeProperty(type, typeInfo));
    }

    private static Stream<AnnotationWrapper> typeInfo(Type type) {
        return type.allInterfaces()
                .flatMap(Elemental::annotationWrappers)
                .filter(annotationWrapper -> annotationWrapper.getAnnotationType().getFullName().equals("jakarta.json.bind.annotation.JsonbTypeInfo"));
    }

    private final AnnotationWrapper typeInfo;

    public TypeProperty(Type type, AnnotationWrapper typeInfo) {
        super(type);
        this.typeInfo = typeInfo;
    }

    @Override public String name() {return typeInfo.getStringProperty("key");}

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
