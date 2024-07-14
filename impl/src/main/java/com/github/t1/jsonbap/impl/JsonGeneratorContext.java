package com.github.t1.jsonbap.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor @Getter @Accessors(fluent = true)
public class JsonGeneratorContext {
    private final boolean writeNullValues;
}
