package com.github.t1.jsonbap.impl;

import com.github.t1.exap.insight.Elemental;
import com.github.t1.jsonbap.api.Bindable;
import lombok.NonNull;

import java.util.Optional;

import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum;
import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.IDENTITY;

public record PropertyConfig(
        PropertyNamingStrategyEnum propertyNamingStrategy) {

    public PropertyConfig(@NonNull Elemental elemental) {this(elemental.findAnnotation(Bindable.class));}

    public PropertyConfig(Optional<Bindable> bindable) {
        this(bindable.map(Bindable::propertyNamingStrategy).orElse(IDENTITY));
    }
}
