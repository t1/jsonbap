package com.github.t1.jsonbap.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.t1.jsonbap.api.Jsonb;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Jsonb
@JsonPropertyOrder(alphabetic = true)
@JsonInclude(Include.NON_NULL)
@Data @Builder @AllArgsConstructor @NoArgsConstructor
@SuppressWarnings("LombokGetterMayBeUsed") // lombok may run after jsonbap
public class Person {
    String firstName;
    String lastName;
    int age;

    Address address;
    Address formerAddress;
    List<String> roles;

    public String getFirstName() {return firstName;}

    public String getLastName() {return lastName;}

    public int getAge() {return age;}

    public Address getAddress() {return address;}

    public Address getFormerAddress() {return formerAddress;}

    public List<String> getRoles() {return roles;}
}
