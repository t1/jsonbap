package com.github.t1.jsonbap.test;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.t1.jsonbap.api.Bindable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Bindable
@JsonPropertyOrder({"@type", "isDog", "name"})
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class Dog implements Pet {
    private String name;
    private final Boolean isDog = true;

    public Dog(String name) {this.name = name;}
}
