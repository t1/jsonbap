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
public class Person {
    private String firstName;
    private String lastName;
    private int age;
    private double averageScore;

    private Address address;
    private Address formerAddress;

    private boolean member;
    private @Singular List<String> roles;
    private long registrationTimestamp;

    private @Singular List<Pet> pets;

    @JsonbNumberFormat(locale = "fr")
    @JsonFormat(shape = STRING, locale = "fr") // this is not enough for Jackson, so we're cheating in JacksonIT
    private BigDecimal income;
}
