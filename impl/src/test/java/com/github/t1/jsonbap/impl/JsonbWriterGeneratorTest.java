package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.jsonbap.api.Jsonb;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.t1.exap.reflection.ReflectionProcessingEnvironment.ENV;
import static javax.tools.StandardLocation.SOURCE_OUTPUT;
import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
class JsonbSerializerGeneratorTest {
    @Getter
    public static class Person {
        String firstName;
        String lastName;
        int age;
        Address address;
        List<String> roles;
    }

    @Jsonb
    @Getter
    public static class Address {
        String street;
        Integer zip;
        String city;
        String state;
        String country;
    }

    @AfterEach
    void tearDown() {
        ENV.clearCreatedResources();
    }

    @Test
    void shouldGeneratePersonWriter() {
        var type = ENV.type(Person.class);

        var generator = new JsonbSerializerGenerator(type);

        var className = generator.className();
        then(className).isEqualTo(Person.class.getSimpleName() + "$$JsonbSerializer");
        try (var typeGenerator = new TypeGenerator(log, type.getPackage(), className)) {
            generator.generate(typeGenerator);
        }
        then(ENV.getCreatedResource(SOURCE_OUTPUT, Person.class.getPackage().getName(), className)).isEqualTo(
                """
                        package com.github.t1.jsonbap.impl;

                        import javax.annotation.processing.Generated;

                        import jakarta.json.bind.serializer.JsonbSerializer;
                        import jakarta.json.bind.serializer.SerializationContext;
                        import jakarta.json.stream.JsonGenerator;

                        @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                        public class Person$$JsonbSerializer implements JsonbSerializer<Person> {
                            @Override
                            public void serialize(Person object, JsonGenerator out, SerializationContext context) {
                                out.writeStartObject();
                                if (object.getAddress() != null) {
                                    context.serialize("address", object.getAddress(), out);
                                } else if (((JsonGeneratorContext) context).writeNullValues()) {
                                    out.writeNull("address");
                                }
                                out.write("age", object.getAge());
                                if (object.getFirstName() != null) {
                                    out.write("firstName", object.getFirstName());
                                } else if (((JsonGeneratorContext) context).writeNullValues()) {
                                    out.writeNull("firstName");
                                }
                                if (object.getLastName() != null) {
                                    out.write("lastName", object.getLastName());
                                } else if (((JsonGeneratorContext) context).writeNullValues()) {
                                    out.writeNull("lastName");
                                }
                                if (object.getRoles() != null) {
                                    context.serialize("roles", object.getRoles(), out);
                                } else if (((JsonGeneratorContext) context).writeNullValues()) {
                                    out.writeNull("roles");
                                }
                                out.writeEnd();
                            }

                        }
                        """);
    }

    @Test
    void shouldGenerateAddressWriter() {
        var type = ENV.type(Address.class);

        var generator = new JsonbSerializerGenerator(type);

        var className = generator.className();
        then(className).isEqualTo(Address.class.getSimpleName() + "$$JsonbSerializer");
        try (var typeGenerator = new TypeGenerator(log, type.getPackage(), className)) {
            generator.generate(typeGenerator);
        }
        then(ENV.getCreatedResource(SOURCE_OUTPUT, Address.class.getPackage().getName(), className)).isEqualTo(
                """
                        package com.github.t1.jsonbap.impl;
                        
                        import javax.annotation.processing.Generated;
                        
                        import jakarta.json.bind.serializer.JsonbSerializer;
                        import jakarta.json.bind.serializer.SerializationContext;
                        import jakarta.json.stream.JsonGenerator;

                        @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                        public class Address$$JsonbSerializer implements JsonbSerializer<Address> {
                            @Override
                            public void serialize(Address object, JsonGenerator out, SerializationContext context) {
                                out.writeStartObject();
                                if (object.getCity() != null) {
                                    out.write("city", object.getCity());
                                } else if (((JsonGeneratorContext) context).writeNullValues()) {
                                    out.writeNull("city");
                                }
                                if (object.getCountry() != null) {
                                    out.write("country", object.getCountry());
                                } else if (((JsonGeneratorContext) context).writeNullValues()) {
                                    out.writeNull("country");
                                }
                                if (object.getState() != null) {
                                    out.write("state", object.getState());
                                } else if (((JsonGeneratorContext) context).writeNullValues()) {
                                    out.writeNull("state");
                                }
                                if (object.getStreet() != null) {
                                    out.write("street", object.getStreet());
                                } else if (((JsonGeneratorContext) context).writeNullValues()) {
                                    out.writeNull("street");
                                }
                                if (object.getZip() != null) {
                                    out.write("zip", object.getZip());
                                } else if (((JsonGeneratorContext) context).writeNullValues()) {
                                    out.writeNull("zip");
                                }
                                out.writeEnd();
                            }
                        
                        }
                        """);
    }
}
