package com.github.t1.jsonbap.impl;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Person {
    String firstName;
    String lastName;
    int age;
    Address address;
    List<String> roles;
}
