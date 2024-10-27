package com.github.t1.jsonbap.impl;

import com.github.t1.exap.insight.ElementalAnnotations;
import com.github.t1.exap.insight.Field;
import com.github.t1.exap.insight.Type;
import lombok.NonNull;

import java.util.stream.Stream;

class FieldProperty extends Property<Field> {
    static Stream<Property<Field>> fieldProperties(
            @NonNull JsonbapConfig jsonbapConfig,
            @NonNull TypeConfig typeConfig,
            @NonNull Type type) {
        return type.getAllFields().stream()
                .map(field -> new FieldProperty(jsonbapConfig, typeConfig, field, field.annotations()));
    }

    private FieldProperty(JsonbapConfig jsonbapConfig, TypeConfig typeConfig, Field field, ElementalAnnotations annotations) {
        super(jsonbapConfig, typeConfig, field, annotations);
    }

    @Override protected Property<?> withAnnotations(@NonNull ElementalAnnotations annotations) {
        return new FieldProperty(this.jsonbapConfig, this.typeConfig, this.elemental, annotations);
    }

    @Override protected String propertyType() {return "field";}

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends Property<?>> Either<T, String> or(T that) {
        return Either.<T, String>value(that).with(Property::isPublic, (T) this);
    }

    @Override protected String typeName() {return elemental().getType().getSimpleName();}

    @Override protected String valueExpression() {return "object." + rawName();}
}
