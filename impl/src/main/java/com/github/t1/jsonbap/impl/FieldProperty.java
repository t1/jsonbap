package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.Field;
import com.github.t1.exap.insight.Type;
import lombok.NonNull;

import java.util.stream.Stream;

class FieldProperty extends Property<Field> {
    static Stream<Property<Field>> fieldProperties(@NonNull Type type) {
        return type.getAllFields().stream()
                .map(FieldProperty::new);
    }

    private final Field field;

    FieldProperty(@NonNull Field field) {super(field); this.field = field;}

    @Override protected void writeTo(TypeGenerator typeGenerator, StringBuilder out) {
        if (field.isTransient()) {
            writeComment(out, field + " is transient");
        } else {
            write(elemental().getType().getSimpleName(), "object." + rawName(), out);
        }
    }
}
