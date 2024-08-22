package com.github.t1.jsonbap.impl;

import com.github.t1.exap.insight.AnnotationWrapper;
import com.github.t1.exap.insight.Elemental;
import com.github.t1.exap.insight.Type;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.function.Consumer;
import java.util.stream.Stream;

class TypeProperties {
    static Stream<Property> typeProperties(Type type) {
        return typeInfo(type).map(typeInfo -> new Property(
                typeInfo.getStringProperty("key"),
                new TypePropertyWriter(type, typeInfo)));
    }

    private static Stream<AnnotationWrapper> typeInfo(Type type) {
        return type.allInterfaces()
                .flatMap(Elemental::annotationWrappers)
                .filter(annotationWrapper -> annotationWrapper.getAnnotationType().getFullName().equals("jakarta.json.bind.annotation.JsonbTypeInfo"));
    }

    private record TypePropertyWriter(Type type, AnnotationWrapper typeInfo) implements Consumer<StringBuilder> {
        @Override public void accept(StringBuilder out) {
            var key = typeInfo.getStringProperty("key");
            if (key == null) key = "@type";
            var list = typeInfo.getAnnotationProperties("value");
            var alias = list.stream()
                    .filter(value -> {
                        var type = value.getProperty("type", DeclaredType.class);
                        var typeName = ((TypeElement) type.asElement()).getQualifiedName().toString();
                        return typeName.equals(this.type.getFullName());
                    })
                    .findAny()
                    .map(value -> value.getStringProperty("alias"))
                    .orElseThrow();
            out.append("        out.write(")
                    .append("\"").append(key).append("\", ")
                    .append("\"").append(alias).append("\");\n");
        }
    }
}
