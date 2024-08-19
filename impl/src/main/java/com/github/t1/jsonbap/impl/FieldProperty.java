package com.github.t1.jsonbap.impl;

import com.github.t1.exap.insight.Field;
import com.github.t1.exap.insight.Type;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.stream.Stream;

@RequiredArgsConstructor
class FieldProperty implements Property {
    static Stream<FieldProperty> fieldProperties(Type type) {
        return type.getAllFields().stream()
                .filter(FieldProperty::isJsonbField)
                .map(FieldProperty::of);
    }

    private static boolean isJsonbField(Field field) {
        return field.isPublic();
    }

    private static FieldProperty of(Field field) {
        return new FieldProperty(field.getType(), field.getName(), "object." + field.getName());
    }

    private final Type type;
    @Getter @Accessors(fluent = true)
    private final String name;
    private final String valueExpression;

    @Override public String toString() {return "field:" + name;}

    @Override public void write(StringBuilder out) {
        if (PRIMITIVE_TYPES.contains(type.getSimpleName())) {
            out.append("        out.write(\"").append(name()).append("\", ")
                    .append(valueExpression).append(");\n");
        } else {
            out.append("        context.serialize(\"").append(name()).append("\", ")
                    .append(valueExpression).append(", out);\n");
        }
    }
}
