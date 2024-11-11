package com.github.t1.jsonbap.test;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.t1.jsonbap.api.Bindable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Bindable
@JsonPropertyOrder({"@type", "isCat", "name"})
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class Cat implements Pet {
    private String name;
    private final Boolean isCat = true;

    public Cat(String name) {this.name = name;}
}
