package com.github.t1.jsonbap.impl;

import com.github.t1.jsonbap.api.Bindable;

import java.util.Optional;

import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum;
import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.IDENTITY;

public record TypeConfig(
        PropertyNamingStrategyEnum propertyNamingStrategy) {

    public TypeConfig(Optional<Bindable> bindable) {
        this(bindable.map(Bindable::propertyNamingStrategy).orElse(IDENTITY));
    }
}
