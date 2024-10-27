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
public class Address {
    private String street;
    private Integer zip;
    private String city;
    private String state;
    private String country;
}
