package com.github.t1.jsonbap.impl;

import java.util.Comparator;

interface Property extends Comparable<Property> {
    Comparator<Property> COMPARATOR = Comparator.comparing(Property::name);

    String name();

    @Override default int compareTo(Property that) {
        return COMPARATOR.compare(this, that);
    }

    void write(StringBuilder out);
}
