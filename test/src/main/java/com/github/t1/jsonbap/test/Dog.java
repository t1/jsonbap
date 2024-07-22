package com.github.t1.jsonbap.test;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.t1.jsonbap.api.Jsonb;
import lombok.NoArgsConstructor;

@Jsonb
@NoArgsConstructor
@JsonPropertyOrder({"@type", "isDog", "name"})
public class Dog implements Pet {
    private String name;
    private final boolean isDog = true;

    public Dog(String name) {this.name = name;}

    public boolean getIsDog() {
        return isDog;
    }

    @Override public String getName() {return name;}
}
