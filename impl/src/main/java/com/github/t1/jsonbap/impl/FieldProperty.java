package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.Field;
import com.github.t1.exap.insight.Type;
import lombok.NonNull;

import java.util.stream.Stream;

class FieldProperty extends Property<Field> {
    static Stream<Property<Field>> fieldProperties(@NonNull TypeConfig config, @NonNull Type type) {
        return type.getAllFields().stream()
                .map(field -> new FieldProperty(config, field));
    }

    FieldProperty(@NonNull TypeConfig config, @NonNull Field field) {super(config, field);}

    @Override public String toString() {return "field " + elemental.name();}

    @Override protected void writeTo(TypeGenerator typeGenerator, StringBuilder out) {
        if (elemental.isTransient()) {
            writeComment(out, this + " is transient");
        } else {
            write(elemental().getType().getSimpleName(), "object." + rawName(), out);
        }
    }
}
