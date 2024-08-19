package com.github.t1.jsonbap.impl;

import lombok.NonNull;

import java.util.Comparator;
import java.util.Set;

interface Property extends Comparable<Property> {
    Comparator<Property> COMPARATOR = Comparator.comparing(Property::name);

    /**
     * The primitive types that JsonGenerator supports directly; no null-check required.
     * All other types are serialized via the context, which does the null-check.
     */
    Set<String> PRIMITIVE_TYPES = Set.of(
            "int",
            "long",
            "double",
            "boolean");

    String name();

    @Override default int compareTo(@NonNull Property that) {return COMPARATOR.compare(this, that);}

    void write(StringBuilder out);
}
