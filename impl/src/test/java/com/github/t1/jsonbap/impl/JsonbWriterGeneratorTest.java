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
class JsonbWriterGeneratorTest {
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

        var generator = new JsonbWriterGenerator(type);

        var className = generator.className();
        then(className).isEqualTo(Person.class.getSimpleName() + "$$JsonbWriter");
        try (var typeGenerator = new TypeGenerator(log, type.getPackage(), className)) {
            generator.generate(typeGenerator);
        }
        then(ENV.getCreatedResource(SOURCE_OUTPUT, Person.class.getPackage().getName(), className)).isEqualTo(
                """
                        package com.github.t1.jsonbap.impl;

                        import javax.annotation.processing.Generated;

                        import com.github.t1.jsonbap.api.JsonbWriter;

                        import jakarta.json.stream.JsonGenerator;

                        @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                        public class Person$$JsonbWriter implements JsonbWriter<Person, JsonGeneratorContext> {
                            @Override
                            public void toJson(Person object, JsonGenerator out, JsonGeneratorContext context) {
                                out.writeStartObject();
                                if (object.getAddress() != null) {
                                    out.writeKey("address");
                                    ApJsonbProvider.jsonbWriterFor(object.getAddress().getClass()).toJson(object.getAddress(), out, context);
                                }
                                out.write("age", object.getAge());
                                if (object.getFirstName() != null) {
                                    out.write("firstName", object.getFirstName());
                                } else if (context.writeNullValues()) {
                                    out.writeNull("firstName");
                                }
                                if (object.getLastName() != null) {
                                    out.write("lastName", object.getLastName());
                                } else if (context.writeNullValues()) {
                                    out.writeNull("lastName");
                                }
                                if (object.getRoles() != null) {
                                    out.writeKey("roles");
                                    ApJsonbProvider.jsonbWriterFor(object.getRoles().getClass()).toJson(object.getRoles(), out, context);
                                }
                                out.writeEnd();
                            }

                        }
                        """);
    }

    @Test
    void shouldGenerateAddressWriter() {
        var type = ENV.type(Address.class);

        var generator = new JsonbWriterGenerator(type);

        var className = generator.className();
        then(className).isEqualTo(Address.class.getSimpleName() + "$$JsonbWriter");
        try (var typeGenerator = new TypeGenerator(log, type.getPackage(), className)) {
            generator.generate(typeGenerator);
        }
        then(ENV.getCreatedResource(SOURCE_OUTPUT, Address.class.getPackage().getName(), className)).isEqualTo(
                """
                        package com.github.t1.jsonbap.impl;

                        import javax.annotation.processing.Generated;

                        import com.github.t1.jsonbap.api.JsonbWriter;

                        import jakarta.json.stream.JsonGenerator;

                        @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                        public class Address$$JsonbWriter implements JsonbWriter<Address, JsonGeneratorContext> {
                            @Override
                            public void toJson(Address object, JsonGenerator out, JsonGeneratorContext context) {
                                out.writeStartObject();
                                if (object.getCity() != null) {
                                    out.write("city", object.getCity());
                                } else if (context.writeNullValues()) {
                                    out.writeNull("city");
                                }
                                if (object.getCountry() != null) {
                                    out.write("country", object.getCountry());
                                } else if (context.writeNullValues()) {
                                    out.writeNull("country");
                                }
                                if (object.getState() != null) {
                                    out.write("state", object.getState());
                                } else if (context.writeNullValues()) {
                                    out.writeNull("state");
                                }
                                if (object.getStreet() != null) {
                                    out.write("street", object.getStreet());
                                } else if (context.writeNullValues()) {
                                    out.writeNull("street");
                                }
                                if (object.getZip() != null) {
                                    out.write("zip", object.getZip());
                                } else if (context.writeNullValues()) {
                                    out.writeNull("zip");
                                }
                                out.writeEnd();
                            }

                        }
                        """);
    }
}
