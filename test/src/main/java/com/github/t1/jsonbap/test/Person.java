package com.github.t1.jsonbap.test;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.t1.jsonbap.api.Bindable;
import jakarta.json.bind.annotation.JsonbNumberFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.With;

import java.math.BigDecimal;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Bindable
@JsonPropertyOrder(alphabetic = true)
@JsonInclude(NON_NULL)
@Data @With @Builder @AllArgsConstructor @NoArgsConstructor
@SuppressWarnings("LombokGetterMayBeUsed") // lombok may run after jsonbap
public class Person {
    String firstName;
    String lastName;
    int age;
    double averageScore;

    Address address;
    Address formerAddress;

    boolean member;
    @Singular List<String> roles;
    long registrationTimestamp;

    @Singular List<Pet> pets;

    @JsonbNumberFormat(locale = "de")
    @JsonFormat(shape = STRING, locale = "de") // this is not enough; we're cheating in JacksonIT
    public BigDecimal income;

    public String getFirstName() {return firstName;}

    public String getLastName() {return lastName;}

    public int getAge() {return age;}

    public double getAverageScore() {return averageScore;}

    public Address getAddress() {return address;}

    public Address getFormerAddress() {return formerAddress;}

    public boolean getMember() {return member;}

    public List<String> getRoles() {return roles;}

    public long getRegistrationTimestamp() {return registrationTimestamp;}

    public List<Pet> getPets() {return pets;}
}
