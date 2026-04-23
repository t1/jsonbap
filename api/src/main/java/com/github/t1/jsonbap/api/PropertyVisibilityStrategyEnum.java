package com.github.t1.jsonbap.api;

import jakarta.json.bind.annotation.JsonbProperty;

/**
 * Allows to define the strategy to derive the JSON field name from a Java property.
 * This never overrides {@link JsonbProperty#value()} annotations.
 */
public enum PropertyNamingStrategyEnum {
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
