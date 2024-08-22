package com.github.t1.jsonbap.impl;

import com.github.t1.exap.insight.Field;
import com.github.t1.exap.insight.Type;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.github.t1.jsonbap.impl.Property.PRIMITIVE_TYPES;

class FieldProperties {
    static Stream<Property> fieldProperties(Type type) {
        return type.getAllFields().stream()
                .filter(FieldProperties::isJsonbField)
                .map(FieldProperties::of);
    }

    private static boolean isJsonbField(Field field) {
        return field.isPublic() && !field.isTransient();
    }

    private static Property of(Field field) {
        return new Property(field.getName(), new FieldPropertyWriter(field.getType(), field.getName(), "object." + field.getName()));
    }

    private record FieldPropertyWriter(
            Type type,
            String name,
            String valueExpression
    ) implements Consumer<StringBuilder> {

        @Override public String toString() {return "field:" + name;}

        @Override public void accept(StringBuilder out) {
            if (PRIMITIVE_TYPES.contains(type.getSimpleName())) {
                out.append("        out.write(\"").append(name).append("\", ")
                        .append(valueExpression).append(");\n");
            } else {
                out.append("        context.serialize(\"").append(name).append("\", ")
                        .append(valueExpression).append(", out);\n");
            }
        }
    }
}
