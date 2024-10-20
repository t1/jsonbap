package com.github.t1.jsonbap.impl;

import com.github.t1.jsonbap.api.Bindable;
import lombok.With;

import java.lang.annotation.Annotation;

import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.IDENTITY;

@With
@SuppressWarnings("ClassExplicitlyAnnotation")
record BindableLiteral(
        Class<?>[] value,
        boolean serializable,
        Bindable.PropertyNamingStrategyEnum propertyNamingStrategy)

        implements Bindable {

    public static final BindableLiteral BINDING = new BindableLiteral(new Class[0], false, IDENTITY);

    @Override public Class<? extends Annotation> annotationType() {return Bindable.class;}
}
