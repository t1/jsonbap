package com.github.t1.jsonbap.impl;

import com.github.t1.exap.reflection.AnnotationWrapper;
import com.github.t1.exap.reflection.Elemental;
import com.github.t1.exap.reflection.Type;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.Optional;

@RequiredArgsConstructor
class TypeProperty implements Property {
    static Optional<TypeProperty> typeProperty(Type type) {
        return typeInfo(type).map(typeInfo -> new TypeProperty(type, typeInfo));
    }

    private static Optional<AnnotationWrapper> typeInfo(Type type) {
        return type.allInterfaces()
                .flatMap(Elemental::annotationWrappers)
                .filter(annotationWrapper -> annotationWrapper.getAnnotationType().getFullName().equals("jakarta.json.bind.annotation.JsonbTypeInfo"))
                .findFirst();
    }

    private final Type type;
    private final AnnotationWrapper typeInfo;

    @Override public String name() {
        return "@type";
    }

    @Override public void write(StringBuilder out) {
        var key = typeInfo.getStringProperty("key");
        if (key == null) key = "@type";
        var list = typeInfo.getAnnotationProperties("value");
        var alias = list.stream()
                .filter(value -> {
                    var type = value.getProperty("type", DeclaredType.class);
                    var typeName = ((TypeElement) type.asElement()).getQualifiedName().toString();
                    return typeName.equals(TypeProperty.this.type.getFullName());
                })
                .findAny()
                .map(value -> value.getStringProperty("alias"))
                .orElseThrow();
        out.append("        out.write(")
                .append("\"").append(key).append("\", ")
                .append("\"").append(alias).append("\");\n");
    }
}
