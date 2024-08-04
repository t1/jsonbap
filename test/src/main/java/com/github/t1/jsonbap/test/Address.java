package com.github.t1.jsonbap.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.t1.jsonbap.api.Bindable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Bindable
@JsonPropertyOrder(alphabetic = true)
@JsonInclude(Include.NON_NULL)
@Data @Builder @AllArgsConstructor @NoArgsConstructor
@SuppressWarnings("LombokGetterMayBeUsed") // lombok may run after jsonbap
public class Address {
    String street;
    Integer zip;
    String city;
    String state;
    String country;

    public String getStreet() {return street;}

    public Integer getZip() {return zip;}

    public String getCity() {return city;}

    public String getState() {return state;}

    public String getCountry() {return country;}
}
