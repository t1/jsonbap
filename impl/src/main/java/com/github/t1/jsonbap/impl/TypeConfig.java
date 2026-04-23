package com.github.t1.jsonbap.impl;

import com.github.t1.jsonbap.api.Bindable;
import com.github.t1.jsonbap.api.PropertyNamingStrategyEnum;

import java.util.Optional;

import static com.github.t1.jsonbap.api.PropertyNamingStrategyEnum.IDENTITY;

public record TypeConfig(
        PropertyNamingStrategyEnum propertyNamingStrategy) {

    public TypeConfig(Optional<Bindable> bindable) {
        this(bindable.map(Bindable::propertyNamingStrategy).orElse(IDENTITY));
    }
}
