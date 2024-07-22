package com.github.t1.jsonbap.test;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.t1.jsonbap.api.Jsonb;
import lombok.NoArgsConstructor;

@Jsonb
@NoArgsConstructor
@JsonPropertyOrder({"@type", "isCat", "name"})
public class Cat implements Pet {
    private String name;
    private final boolean isCat = true;

    public Cat(String name) {this.name = name;}

    public boolean getIsCat() { return isCat; }

    @Override public String getName() {return name;}
}
