package com.github.t1.jsonbap.api;

import jakarta.json.bind.annotation.JsonbProperty;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.IDENTITY;
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

    /**
     * Allows to define the strategy to derive the JSON field name from a Java property.
     * This never overrides {@link JsonbProperty#value()} annotations.
     */
    enum PropertyNamingStrategyEnum {
        /**
         * Using this strategy, the property name is unchanged from its normally camel case form.
         *
         * @see <a href="https://en.wikipedia.org/wiki/Letter_case#Camel_case">Camel case</a>
         */
        IDENTITY,

        /**
         * Using this strategy, the property name is transformed to lower case with dashes.
         * The dashes are on the positions of different case boundaries in the original field name (camel case).
         *
         * @see <a href="https://en.wikipedia.org/wiki/Letter_case#Kebab_case">Kebab case</a>
         */
        LOWER_CASE_WITH_DASHES,

        /**
         * Using this strategy, the property name is transformed to lower case with underscores.
         * The underscores are on the positions of different case boundaries in the original field name (camel case).
         *
         * @see <a href="https://en.wikipedia.org/wiki/Letter_case#Snake_case">Snake case</a>
         */
        LOWER_CASE_WITH_UNDERSCORES,

        /**
         * Using this strategy, the property name is transformed to upper case with underscores.
         * The underscores are on the positions of different case boundaries in the original field name (camel case).
         *
         * @see <a href="https://en.wikipedia.org/wiki/Letter_case#Snake_case">Snake case</a>
         */
        UPPER_CASE_WITH_UNDERSCORES,

        /**
         * Using this strategy, the first character will be capitalized.
         *
         * @see <a href="https://en.wikipedia.org/wiki/Letter_case#Camel_case">Camel case</a>
         */
        UPPER_CAMEL_CASE,

        /**
         * Using this strategy, the first character will be capitalized and the words
         * will be separated by spaces.
         *
         * @see <a href="https://en.wikipedia.org/wiki/Title_case">Title case (roughly)</a>
         */
        UPPER_CAMEL_CASE_WITH_SPACES,

        /**
         * Using this strategy, the serialization will be same as identity.
         * Deserialization will be case insensitive. E.g. property in JSON with name
         * PropertyNAME, will be mapped to field propertyName.
         */
        CASE_INSENSITIVE
    }
}
