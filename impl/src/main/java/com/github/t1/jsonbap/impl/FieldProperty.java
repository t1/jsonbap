package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.Field;
import com.github.t1.exap.insight.Type;
import lombok.NonNull;

import java.util.stream.Stream;

class FieldProperty extends Property {
    static Stream<Property> fieldProperties(Type type) {
        return type.getAllFields().stream()
                .map(FieldProperty::new);
    }

    private final Field field;

    FieldProperty(@NonNull Field field) {super(field);
        this.field = field;
    }

    @Override protected void writeTo(TypeGenerator typeGenerator, StringBuilder out) {
        if (!field.isPublic() || field.isTransient()) return;
        if (PRIMITIVE_TYPES.contains(((Field) elemental()).getType().getSimpleName())) {
            out.append("        out.write(\"").append(name()).append("\", ")
                    .append("object.").append(name()).append(");\n");
        } else {
            out.append("        context.serialize(\"").append(name()).append("\", ")
                    .append("object.").append(name()).append(", out);\n");
        }
    }
}
