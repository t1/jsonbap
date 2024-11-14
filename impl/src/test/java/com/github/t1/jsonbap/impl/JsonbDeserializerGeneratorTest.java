package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.jsonbap.api.Bindable;
import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.github.t1.exap.reflection.ReflectionProcessingEnvironment.ENV;
import static com.github.t1.jsonbap.impl.JsonbSerializerGeneratorTest.relativeName;
import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.StandardLocation.SOURCE_OUTPUT;
import static org.assertj.core.api.BDDAssertions.then;

class JsonbDeserializerGeneratorTest {
    private static void generate(Class<?> deserializableClass) {
        var messages = generator(deserializableClass);
        then(messages).isEmpty();
    }

    // TODO can we merge this logic with the one in JsonbAnnotationProcessor#process?
    private static List<String> generator(Class<?> deserializableClass) {
        var type = ENV.type(deserializableClass);
        var bindable = type.annotation(Bindable.class);
        if (!bindable.map(Bindable::deserializable).orElse(true)) return List.of();
        var generator = new JsonbDeserializerGenerator(new JsonbapConfig(), type, new TypeConfig(bindable));
        var className = generator.className();

        try (var typeGenerator = new TypeGenerator(ENV.round(), type.getPackage(), className)) {
            generator.generate(typeGenerator);
        }

        return ENV.getMessages(null, ERROR);
    }

    private static String generated(Class<?> type) {
        return ENV.getCreatedResource(SOURCE_OUTPUT, type.getPackage().getName(),
                relativeName(type) + "$$JsonbDeserializer");
    }

    @AfterEach
    void tearDown() {
        ENV.clearCreatedResources();
        ENV.getMessages().clear();
    }


    @Bindable(deserializable = false)
    @Data
    public static class NotDeserializable {
        private String notDeserializable;
    }

    @Test
    void shouldNotGenerateDeserializerForNotDeserializableClass() {
        generate(NotDeserializable.class);

        then(generated(NotDeserializable.class)).isNull();
    }


    @Data
    public static class OptionalDeserializable {
        private Optional<String> optionalDeserializable;
    }

    @Test
    void shouldGenerateDeserializerForOptionalString() {
        generate(OptionalDeserializable.class);

        then(generated(OptionalDeserializable.class)).isEqualTo("""
                package com.github.t1.jsonbap.impl;
                
                import java.lang.reflect.Type;
                
                import javax.annotation.processing.Generated;
                
                import com.github.t1.jsonbap.runtime.FluentParser;
                
                import jakarta.json.bind.serializer.DeserializationContext;
                import jakarta.json.bind.serializer.JsonbDeserializer;
                import jakarta.json.stream.JsonParser;
                import jakarta.json.stream.JsonParser.Event;
                
                @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                public class JsonbDeserializerGeneratorTest$OptionalDeserializable$$JsonbDeserializer implements JsonbDeserializer<JsonbDeserializerGeneratorTest$OptionalDeserializable> {
                
                    @Override
                    public JsonbDeserializerGeneratorTest$OptionalDeserializable deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
                        var parser = new FluentParser(jsonParser);
                        if (parser.is(Event.VALUE_NULL)) return null;
                        var object = new JsonbDeserializerGeneratorTest$OptionalDeserializable();
                        parser.assume(Event.START_OBJECT);
                        while (parser.next().is(Event.KEY_NAME)) {
                            switch (parser.StringAndNext()) {
                                case "optionalDeserializable" -> parser.readString().ifPresent(value -> object.setOptionalDeserializable(value));
                            }
                        }
                        parser.assume(Event.END_OBJECT);
                        return object;
                    }
                }
                """);
    }


    @Test
    void shouldGenerateAddressDeserializer() {
        generate(Address.class);

        then(generated(Address.class)).isEqualTo(
                """
                        package com.github.t1.jsonbap.impl;
                        
                        import java.lang.reflect.Type;
                        
                        import javax.annotation.processing.Generated;
                        
                        import com.github.t1.jsonbap.runtime.FluentParser;
                        
                        import jakarta.json.bind.serializer.DeserializationContext;
                        import jakarta.json.bind.serializer.JsonbDeserializer;
                        import jakarta.json.stream.JsonParser;
                        import jakarta.json.stream.JsonParser.Event;
                        
                        @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                        public class Address$$JsonbDeserializer implements JsonbDeserializer<Address> {
                        
                            @Override
                            public Address deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
                                var parser = new FluentParser(jsonParser);
                                if (parser.is(Event.VALUE_NULL)) return null;
                                var object = new Address();
                                parser.assume(Event.START_OBJECT);
                                while (parser.next().is(Event.KEY_NAME)) {
                                    switch (parser.StringAndNext()) {
                                        case "city" -> parser.readString().ifPresent(value -> object.setCity(value));
                                        case "country" -> parser.readString().ifPresent(value -> object.setCountry(value));
                                        case "state" -> parser.readString().ifPresent(value -> object.setState(value));
                                        case "street" -> parser.readString().ifPresent(value -> object.setStreet(value));
                                        case "zip" -> parser.readInteger().ifPresent(value -> object.setZip(value));
                                    }
                                }
                                parser.assume(Event.END_OBJECT);
                                return object;
                            }
                        }
                        """);
    }

    @Test
    @Disabled("collections are not yet supported, yet")
    void shouldGeneratePersonDeserializer() {
        generate(Person.class);

        then(generated(Person.class)).isEqualTo(
                """
                        package com.github.t1.jsonbap.impl;
                        
                        import java.lang.reflect.Type;
                        
                        import javax.annotation.processing.Generated;
                        
                        import com.github.t1.jsonbap.runtime.FluentParser;
                        
                        import jakarta.json.bind.serializer.DeserializationContext;
                        import jakarta.json.bind.serializer.JsonbDeserializer;
                        import jakarta.json.stream.JsonParser;
                        import jakarta.json.stream.JsonParser.Event;
                        
                        @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                        public class Person$$JsonbDeserializer implements JsonbDeserializer<Person> {
                        
                            @Override
                            public Person deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
                                var parser = new FluentParser(jsonParser);
                                if (parser.is(Event.VALUE_NULL)) return null;
                                var object = new Person();
                                parser.assume(Event.START_OBJECT);
                                while (parser.next().is(Event.KEY_NAME)) {
                                    switch (parser.getString()) {
                                        case "address" -> object.setAddress(ctx.deserialize(Address.class, jsonParser));
                                        case "age" -> parser.Integer().ifPresent(value -> object.setAge(value));
                                        case "firstName" -> parser.String().ifPresent(value -> object.setFirstName(value));
                                        case "lastName" -> parser.String().ifPresent(value -> object.setLastName(value));
                                        case "roles" -> object.setRoles(Stream.of(ctx.deserialize(String[].class, jsonParser)).toList());
                                    }
                                }
                                parser.assume(Event.END_OBJECT);
                                return object;
                            }
                        }
                        """);
    }
}
