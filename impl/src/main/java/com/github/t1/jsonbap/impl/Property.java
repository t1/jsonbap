package com.github.t1.jsonbap.impl;

import lombok.NonNull;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Consumer;

record Property(String name, Consumer<StringBuilder> writer) implements Comparable<Property> {
    private static final Comparator<Property> COMPARATOR = Comparator.comparing(Property::name);

    /**
     * The primitive types that JsonGenerator supports directly; no null-check required.
     * All other types are serialized via the context, which does the null-check.
     */
    static final Set<String> PRIMITIVE_TYPES = Set.of(
            "int",
            "long",
            "double",
            "boolean");

    @Override public int compareTo(@NonNull Property that) {return COMPARATOR.compare(this, that);}

    public void write(StringBuilder out) {writer.accept(out);}

    public Property merge(Property q) {
        return q;
    }
}
