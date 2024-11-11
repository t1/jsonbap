package com.github.t1.jsonbap.impl;

import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;

@JsonbTypeInfo({
        @JsonbSubtype(alias = "cat", type = Cat.class),
        @JsonbSubtype(alias = "dog", type = Dog.class)
})
interface Pet {}
