package com.github.t1.jsonbap.impl;

import lombok.Getter;

import java.util.List;

@Getter
public class Person {
    String firstName;
    String lastName;
    int age;
    Address address;
    List<String> roles;
}
