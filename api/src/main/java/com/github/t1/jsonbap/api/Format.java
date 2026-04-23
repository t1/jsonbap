package com.github.t1.jsonbap.api;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbNumberFormat;
import jakarta.json.bind.config.PropertyOrderStrategy;

import static com.github.t1.jsonbap.api.PropertyNamingStrategyEnum.IDENTITY;
import static com.github.t1.jsonbap.api.PropertyVisibilityStrategyEnum.PUBLIC;

/// Define a format for serializing and deserializing properties.
/// E.g. on a JAX-RS resource class supporting a legacy version `v1` using Kebab case and null values,
/// and a version `v2` with the default behavior, you could use:
///
/// ```java
///
///@Path("/products")publicclassProducts{
///@GET
///@Path("/v1/{id}")
///@Format(propertyNamingStrategy=LOWER_CASE_WITH_DASHES,nillable=true)
///             public Product getV1Product(@PathParam("id") String id){
///                return getV2Product(id);
///}
///@GET
///@Path("/v2/{id}")publicProductgetV2Product(@PathParam("id")String id){
///                return ...
///}
///}
///```
public @interface Format {
    String name() default "";

    JsonbDateFormat dateFormat() default @JsonbDateFormat;

    JsonbNumberFormat numberFormat() default @JsonbNumberFormat;

    boolean nillable() default false;

    PropertyVisibilityStrategyEnum visibility() default PUBLIC;

    PropertyNamingStrategyEnum propertyNamingStrategy() default IDENTITY;

    String propertyOrderStrategy() default PropertyOrderStrategy.ANY;
}
