package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.Type;
import com.github.t1.jsonbap.api.Bindable;
import jakarta.json.bind.annotation.JsonbNumberFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.github.t1.exap.reflection.ReflectionProcessingEnvironment.ENV;
import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.LOWER_CASE_WITH_DASHES;
import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.StandardLocation.SOURCE_OUTPUT;
import static org.assertj.core.api.BDDAssertions.then;

class JsonbSerializerGeneratorTest {
    @JsonbTypeInfo({
            @JsonbSubtype(alias = "cat", type = Cat.class),
            @JsonbSubtype(alias = "dog", type = Dog.class)
    })
    interface Pet {}

    @Bindable
    public static class Dog implements Pet {
        @SuppressWarnings("unused")
        public boolean getIsDog() {return true;}
    }

    @AfterEach
    void tearDown() {
        ENV.clearCreatedResources();
    }


    @Test
    void shouldGeneratePersonWriter() {
        generate(Person.class);

        then(ENV.getCreatedResource(SOURCE_OUTPUT, Person.class.getPackage().getName(), "Person$$JsonbSerializer")).isEqualTo(
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
                                context.serialize("address", object.getAddress(), out);
                                out.write("age", object.getAge());
                                context.serialize("firstName", object.getFirstName(), out);
                                context.serialize("lastName", object.getLastName(), out);
                                context.serialize("roles", object.getRoles(), out);
                                out.writeEnd();
                            }
                        }
                        """);
    }

    @Test
    void shouldGenerateAddressWriter() {
        generate(Address.class);

        then(ENV.getCreatedResource(SOURCE_OUTPUT, Address.class.getPackage().getName(), "Address$$JsonbSerializer")).isEqualTo(
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
                                context.serialize("city", object.getCity(), out);
                                context.serialize("country", object.getCountry(), out);
                                context.serialize("state", object.getState(), out);
                                context.serialize("street", object.getStreet(), out);
                                context.serialize("zip", object.getZip(), out);
                                out.writeEnd();
                            }
                        }
                        """);
    }

    @Test
    void shouldGenerateCatWriter() {
        generate(Cat.class);

        then(ENV.getCreatedResource(SOURCE_OUTPUT, Cat.class.getPackage().getName(), "Cat$$JsonbSerializer")).isEqualTo(
                """
                        package com.github.t1.jsonbap.impl;
                        
                        import javax.annotation.processing.Generated;
                        
                        import jakarta.json.bind.serializer.JsonbSerializer;
                        import jakarta.json.bind.serializer.SerializationContext;
                        import jakarta.json.stream.JsonGenerator;
                        
                        @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                        public class Cat$$JsonbSerializer implements JsonbSerializer<Cat> {
                        
                            @Override
                            public void serialize(Cat object, JsonGenerator out, SerializationContext context) {
                                out.writeStartObject();
                                context.serialize("@type", "cat", out);
                                out.write("isCat", object.getIsCat());
                                out.writeEnd();
                            }
                        }
                        """);
    }


    @SuppressWarnings("unused")
    public static class TransientContainer {
        public transient String transientField;
        public String normalField;
    }

    @Test
    void shouldGenerateTransientWriter() {
        generate(TransientContainer.class);

        then(ENV.getCreatedResource(SOURCE_OUTPUT, TransientContainer.class.getPackage().getName(),
                "JsonbSerializerGeneratorTest$TransientContainer$$JsonbSerializer")).isEqualTo(
                """
                        package com.github.t1.jsonbap.impl;
                        
                        import javax.annotation.processing.Generated;
                        
                        import jakarta.json.bind.serializer.JsonbSerializer;
                        import jakarta.json.bind.serializer.SerializationContext;
                        import jakarta.json.stream.JsonGenerator;
                        
                        @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                        public class JsonbSerializerGeneratorTest$TransientContainer$$JsonbSerializer implements JsonbSerializer<JsonbSerializerGeneratorTest$TransientContainer> {
                        
                            @Override
                            public void serialize(JsonbSerializerGeneratorTest$TransientContainer object, JsonGenerator out, SerializationContext context) {
                                out.writeStartObject();
                                context.serialize("normalField", object.normalField, out);
                                // field "transientField" is transient
                                out.writeEnd();
                            }
                        }
                        """);
    }


    @SuppressWarnings("unused")
    @Bindable(propertyNamingStrategy = LOWER_CASE_WITH_DASHES)
    public static class KebabCaseFieldContainer {
        public String kebabField;
    }

    @Test
    void shouldGenerateKebabCaseFieldWriter() {
        generate(KebabCaseFieldContainer.class);

        then(ENV.getCreatedResource(SOURCE_OUTPUT, KebabCaseFieldContainer.class.getPackage().getName(),
                "JsonbSerializerGeneratorTest$KebabCaseFieldContainer$$JsonbSerializer")).isEqualTo(
                """
                        package com.github.t1.jsonbap.impl;
                        
                        import javax.annotation.processing.Generated;
                        
                        import jakarta.json.bind.serializer.JsonbSerializer;
                        import jakarta.json.bind.serializer.SerializationContext;
                        import jakarta.json.stream.JsonGenerator;
                        
                        @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                        public class JsonbSerializerGeneratorTest$KebabCaseFieldContainer$$JsonbSerializer implements JsonbSerializer<JsonbSerializerGeneratorTest$KebabCaseFieldContainer> {
                        
                            @Override
                            public void serialize(JsonbSerializerGeneratorTest$KebabCaseFieldContainer object, JsonGenerator out, SerializationContext context) {
                                out.writeStartObject();
                                // name derived from field name with strategy LOWER_CASE_WITH_DASHES
                                context.serialize("kebab-field", object.kebabField, out);
                                out.writeEnd();
                            }
                        }
                        """);
    }


    @SuppressWarnings("unused")
    @Bindable(propertyNamingStrategy = LOWER_CASE_WITH_DASHES)
    public static class KebabCaseGetterContainer {
        public String getKebabValue() {return "kebab";}
    }

    @Test
    void shouldGenerateKebabCaseGetterWriter() {
        generate(KebabCaseGetterContainer.class);

        then(ENV.getCreatedResource(SOURCE_OUTPUT, KebabCaseGetterContainer.class.getPackage().getName(),
                "JsonbSerializerGeneratorTest$KebabCaseGetterContainer$$JsonbSerializer")).isEqualTo(
                """
                        package com.github.t1.jsonbap.impl;
                        
                        import javax.annotation.processing.Generated;
                        
                        import jakarta.json.bind.serializer.JsonbSerializer;
                        import jakarta.json.bind.serializer.SerializationContext;
                        import jakarta.json.stream.JsonGenerator;
                        
                        @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                        public class JsonbSerializerGeneratorTest$KebabCaseGetterContainer$$JsonbSerializer implements JsonbSerializer<JsonbSerializerGeneratorTest$KebabCaseGetterContainer> {
                        
                            @Override
                            public void serialize(JsonbSerializerGeneratorTest$KebabCaseGetterContainer object, JsonGenerator out, SerializationContext context) {
                                out.writeStartObject();
                                // name derived from getter name with strategy LOWER_CASE_WITH_DASHES
                                context.serialize("kebab-value", object.getKebabValue(), out);
                                out.writeEnd();
                            }
                        }
                        """);
    }


    @SuppressWarnings("unused")
    public static class FormattedNumberContainer {
        @JsonbNumberFormat(locale = "de")
        public long formattedField;
    }

    @Test
    void shouldGenerateFormattedNumberWriter() {
        generate(FormattedNumberContainer.class);

        then(ENV.getCreatedResource(SOURCE_OUTPUT, FormattedNumberContainer.class.getPackage().getName(),
                "JsonbSerializerGeneratorTest$FormattedNumberContainer$$JsonbSerializer")).isEqualTo(
                """
                        package com.github.t1.jsonbap.impl;
                        
                        import java.text.NumberFormat;
                        import java.util.Locale;
                        import java.util.Optional;
                        
                        import javax.annotation.processing.Generated;
                        
                        import jakarta.json.bind.serializer.JsonbSerializer;
                        import jakarta.json.bind.serializer.SerializationContext;
                        import jakarta.json.stream.JsonGenerator;
                        
                        @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                        public class JsonbSerializerGeneratorTest$FormattedNumberContainer$$JsonbSerializer implements JsonbSerializer<JsonbSerializerGeneratorTest$FormattedNumberContainer> {
                        
                            @Override
                            public void serialize(JsonbSerializerGeneratorTest$FormattedNumberContainer object, JsonGenerator out, SerializationContext context) {
                                out.writeStartObject();
                                // number format from @jakarta.json.bind.annotation.JsonbNumberFormat(locale="de", value="") on field com.github.t1.jsonbap.impl.JsonbSerializerGeneratorTest$FormattedNumberContainer#formattedField
                                context.serialize("formattedField", Optional.ofNullable(object.formattedField)
                                    .map(NumberFormat.getInstance(Locale.of("de"))::format)
                                    .orElse(null), out);
                                out.writeEnd();
                            }
                        }
                        """);
    }

    private static void generate(Class<?> serializableClass) {
        var type = ENV.type(serializableClass);
        generate(type, type.annotation(Bindable.class));
    }

    private static void generate(Type type, Optional<Bindable> bindable) {
        var generator = new JsonbSerializerGenerator(new JsonbapConfig(), type, new TypeConfig(bindable));
        var className = generator.className();

        try (var typeGenerator = new TypeGenerator(ENV.round(), type.getPackage(), className)) {
            generator.generate(typeGenerator);
        }
        then(ENV.getMessages(null, ERROR)).isEmpty();
    }
}
