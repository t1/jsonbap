package com.github.t1.jsonbap.api;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.github.t1.jsonbap.api.PropertyNamingStrategyEnum.IDENTITY;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Create the necessary serializer/deserializer classes for the annotated class.
 * They are named like the class with a <code>$$JsonbSerializer</code> respectively <code>$$JsonbDeserializer</code> suffix.
 */
@Retention(RUNTIME)
@Target(TYPE)
// TODO support package-wide annotation
public @interface Bindable {
    /**
     * Additional classes that the annotation processor should generate a serializer/deserializer class for.
     * This is necessary for classes that you cannot annotate yourself, like classes from the JDK or third-party libraries.
     */
    Class<?>[] value() default {};

    /**
     * Should a serializer be generated?
     */
    boolean serializable() default true;

    /**
     * Should a deserializer be generated?
     */
    boolean deserializable() default true;

    PropertyNamingStrategyEnum propertyNamingStrategy() default IDENTITY;
}
