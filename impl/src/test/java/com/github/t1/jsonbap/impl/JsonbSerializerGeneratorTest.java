package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.jsonbap.api.Bindable;
import jakarta.json.bind.annotation.JsonbNumberFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;

import static com.github.t1.exap.reflection.ReflectionProcessingEnvironment.ENV;
import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.LOWER_CASE_WITH_DASHES;
import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.StandardLocation.SOURCE_OUTPUT;
import static org.assertj.core.api.BDDAssertions.then;

class JsonbSerializerGeneratorTest {
    private static void generate(Class<?> serializableClass) {
        var messages = generator(serializableClass);
        then(messages).isEmpty();
    }

    // TODO can we merge this logic with the one in JsonbAnnotationProcessor#process?
    private static List<String> generator(Class<?> serializableClass) {
        var type = ENV.type(serializableClass);
        var bindable = type.annotation(Bindable.class);
        if (!bindable.map(Bindable::serializable).orElse(true)) return List.of();
        var generator = new JsonbSerializerGenerator(new JsonbapConfig(), type, new TypeConfig(bindable));
        var className = generator.className();

        try (var typeGenerator = new TypeGenerator(ENV.round(), type.getPackage(), className)) {
            generator.generate(typeGenerator);
        }

        return ENV.getMessages(null, ERROR);
    }

    private static String generated(Class<?> type) {
        return ENV.getCreatedResource(SOURCE_OUTPUT, type.getPackage().getName(),
                relativeName(type) + "$$JsonbSerializer");
    }

    static String relativeName(Class<?> type) {
        var packageLength = type.getPackage().getName().length();
        if (packageLength > 0)
            ++packageLength; // for the final dot
        var relativeName = type.getName().substring(packageLength);
        var typeParameters = relativeName.indexOf('<');
        if (typeParameters >= 0)
            relativeName = relativeName.substring(0, typeParameters);
        return relativeName;
    }

    @AfterEach
    void tearDown() {
        ENV.clearCreatedResources();
        ENV.getMessages().clear();
    }

    @Bindable(serializable = false)
    @Data
    public static class NotSerializable {
        private String notSerializable;
    }

    @Test
    void shouldNotGenerateSerializerForNotSerializableClass() {
        generate(NotSerializable.class);

        then(generated(NotSerializable.class)).isNull();
    }

    @Test
    void shouldGenerateAddressSerializer() {
        generate(Address.class);

        then(generated(Address.class)).isEqualTo(
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
    void shouldGeneratePersonSerializer() {
        generate(Person.class);

        then(generated(Person.class)).isEqualTo(
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
    void shouldGenerateCatSerializer() {
        generate(Cat.class);

        then(generated(Cat.class)).isEqualTo(
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
    void shouldGenerateTransientSerializer() {
        generate(TransientContainer.class);

        then(generated(TransientContainer.class)).isEqualTo(
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
    void shouldGenerateKebabCaseFieldSerializer() {
        generate(KebabCaseFieldContainer.class);

        then(generated(KebabCaseFieldContainer.class)).isEqualTo(
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
    void shouldGenerateKebabCaseGetterSerializer() {
        generate(KebabCaseGetterContainer.class);

        then(generated(KebabCaseGetterContainer.class)).isEqualTo(
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
    void shouldGenerateFormattedNumberSerializer() {
        generate(FormattedNumberContainer.class);

        then(generated(FormattedNumberContainer.class)).isEqualTo(
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

    @Setter @Getter public static class DuplicateNameContainer {
        private String firstInstance;

        @JsonbProperty("firstInstance")
        private String secondInstance;
    }

    @Test
    public void shouldFailToGenerateDuplicateSerializer() {
        var messages = generator(DuplicateNameContainer.class);

        then(messages).containsExactly("Duplicate property name: field \"firstInstance\" and field \"secondInstance\"" +
                                       " in " + DuplicateNameContainer.class);
    }

    private static void generate(Class<?> serializableClass) {
        var messages = generator(serializableClass);
        then(messages).isEmpty();
    }
}
