package com.github.t1.jsonbap.impl;

import com.github.t1.jsonbap.api.Bindable;
import lombok.Getter;
import lombok.Setter;

@Bindable
@Getter @Setter
public class Address {
    String street;
    Integer zip;
    String city;
    String state;
    String country;
}
