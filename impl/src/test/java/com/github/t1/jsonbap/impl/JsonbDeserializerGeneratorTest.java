package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.Message;
import com.github.t1.jsonbap.api.Bindable;
import jakarta.json.bind.annotation.JsonbTypeDeserializer;
import jakarta.json.bind.annotation.JsonbTypeSerializer;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonParser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.t1.exap.reflection.ReflectionProcessingEnvironment.ENV;
import static com.github.t1.jsonbap.impl.JsonbSerializerGeneratorTest.relativeName;
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

        return ENV.messages()
                .filter(Message::isError)
                .map(message -> message.getElemental() + ": " + message.getText())
                .toList();
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
                                case "optionalDeserializable" -> object.setOptionalDeserializable(parser.readString());
                            }
                        }
                        parser.assume(Event.END_OBJECT);
                        return object;
                    }
                }
                """);
    }


    @Data
    public static class OptionalTypeContainer implements Container<Optional<StringContainer>> {
        private Optional<StringContainer> element;
    }

    @Test
    void shouldGenerateDeserializerForOptionalTypeContainer() {
        generate(OptionalTypeContainer.class);

        then(generated(OptionalTypeContainer.class)).isEqualTo("""
                package com.github.t1.jsonbap.impl;
                
                import java.lang.reflect.Type;
                import java.util.Optional;
                
                import javax.annotation.processing.Generated;
                
                import com.github.t1.jsonbap.runtime.FluentParser;
                
                import jakarta.json.bind.serializer.DeserializationContext;
                import jakarta.json.bind.serializer.JsonbDeserializer;
                import jakarta.json.stream.JsonParser;
                import jakarta.json.stream.JsonParser.Event;
                
                @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                public class JsonbDeserializerGeneratorTest$OptionalTypeContainer$$JsonbDeserializer implements JsonbDeserializer<JsonbDeserializerGeneratorTest$OptionalTypeContainer> {
                
                    @Override
                    public JsonbDeserializerGeneratorTest$OptionalTypeContainer deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
                        var parser = new FluentParser(jsonParser);
                        if (parser.is(Event.VALUE_NULL)) return null;
                        var object = new JsonbDeserializerGeneratorTest$OptionalTypeContainer();
                        parser.assume(Event.START_OBJECT);
                        while (parser.next().is(Event.KEY_NAME)) {
                            switch (parser.StringAndNext()) {
                                case "element" -> object.setElement(Optional.ofNullable(ctx.deserialize(JsonbDeserializerGeneratorTest$StringContainer.class, jsonParser)));
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
                                    switch (parser.StringAndNext()) {
                                        case "address" -> object.setAddress(ctx.deserialize(Address.class, jsonParser));
                                        case "age" -> parser.readInteger().ifPresent(value -> object.setAge(value));
                                        case "firstName" -> parser.readString().ifPresent(value -> object.setFirstName(value));
                                        case "lastName" -> parser.readString().ifPresent(value -> object.setLastName(value));
                                        case "roles" -> object.setRoles(Stream.of(ctx.deserialize(String[].class, jsonParser)).toList());
                                    }
                                }
                                parser.assume(Event.END_OBJECT);
                                return object;
                            }
                        }
                        """);
    }


    @Setter @Getter
    public static class BinaryDataContainer {
        private byte[] data = "Test String".getBytes();
    }

    @Test
    void shouldGenerateBinaryDataDeserializer() {
        generate(BinaryDataContainer.class);

        then(generated(BinaryDataContainer.class)).isEqualTo(
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
                        public class JsonbDeserializerGeneratorTest$BinaryDataContainer$$JsonbDeserializer implements JsonbDeserializer<JsonbDeserializerGeneratorTest$BinaryDataContainer> {
                        
                            @Override
                            public JsonbDeserializerGeneratorTest$BinaryDataContainer deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
                                var parser = new FluentParser(jsonParser);
                                if (parser.is(Event.VALUE_NULL)) return null;
                                var object = new JsonbDeserializerGeneratorTest$BinaryDataContainer();
                                parser.assume(Event.START_OBJECT);
                                while (parser.next().is(Event.KEY_NAME)) {
                                    switch (parser.StringAndNext()) {
                                        case "data" -> object.setData(ctx.deserialize(byte[].class, jsonParser));
                                    }
                                }
                                parser.assume(Event.END_OBJECT);
                                return object;
                            }
                        }
                        """);
    }

    @Getter @Setter
    public static class OptionalArrayContainer implements Container<Optional<String>[]> {
        private Optional<String>[] element;
    }

    @Test
    void shouldGenerateOptionalArrayDeserializer() {
        generate(OptionalArrayContainer.class);

        then(generated(OptionalArrayContainer.class)).isEqualTo(
                """
                        package com.github.t1.jsonbap.impl;
                        
                        import java.lang.reflect.Type;
                        import java.util.Optional;
                        
                        import javax.annotation.processing.Generated;
                        
                        import com.github.t1.jsonbap.runtime.FluentParser;
                        import com.github.t1.jsonbap.runtime.TypeLiteral;
                        
                        import jakarta.json.bind.serializer.DeserializationContext;
                        import jakarta.json.bind.serializer.JsonbDeserializer;
                        import jakarta.json.stream.JsonParser;
                        import jakarta.json.stream.JsonParser.Event;
                        
                        @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                        public class JsonbDeserializerGeneratorTest$OptionalArrayContainer$$JsonbDeserializer implements JsonbDeserializer<JsonbDeserializerGeneratorTest$OptionalArrayContainer> {
                        
                            @Override
                            public JsonbDeserializerGeneratorTest$OptionalArrayContainer deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
                                var parser = new FluentParser(jsonParser);
                                if (parser.is(Event.VALUE_NULL)) return null;
                                var object = new JsonbDeserializerGeneratorTest$OptionalArrayContainer();
                                parser.assume(Event.START_OBJECT);
                                while (parser.next().is(Event.KEY_NAME)) {
                                    switch (parser.StringAndNext()) {
                                        case "element" -> object.setElement(ctx.deserialize(TypeLiteral.genericType(new TypeLiteral<Optional<String>[]>() {}), jsonParser));
                                    }
                                }
                                parser.assume(Event.END_OBJECT);
                                return object;
                            }
                        }
                        """);
    }


    @Getter @Setter
    public static class ListArrayContainer implements Container<List<String>[]> {
        private List<String>[] element;
    }

    @Test
    void shouldGenerateListArrayDeserializer() {
        generate(ListArrayContainer.class);

        then(generated(ListArrayContainer.class)).isEqualTo(
                """
                        package com.github.t1.jsonbap.impl;
                        
                        import java.lang.reflect.Type;
                        import java.util.List;
                        
                        import javax.annotation.processing.Generated;
                        
                        import com.github.t1.jsonbap.runtime.FluentParser;
                        import com.github.t1.jsonbap.runtime.TypeLiteral;
                        
                        import jakarta.json.bind.serializer.DeserializationContext;
                        import jakarta.json.bind.serializer.JsonbDeserializer;
                        import jakarta.json.stream.JsonParser;
                        import jakarta.json.stream.JsonParser.Event;
                        
                        @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                        public class JsonbDeserializerGeneratorTest$ListArrayContainer$$JsonbDeserializer implements JsonbDeserializer<JsonbDeserializerGeneratorTest$ListArrayContainer> {
                        
                            @Override
                            public JsonbDeserializerGeneratorTest$ListArrayContainer deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
                                var parser = new FluentParser(jsonParser);
                                if (parser.is(Event.VALUE_NULL)) return null;
                                var object = new JsonbDeserializerGeneratorTest$ListArrayContainer();
                                parser.assume(Event.START_OBJECT);
                                while (parser.next().is(Event.KEY_NAME)) {
                                    switch (parser.StringAndNext()) {
                                        case "element" -> object.setElement(ctx.deserialize(TypeLiteral.genericType(new TypeLiteral<List<String>[]>() {}), jsonParser));
                                    }
                                }
                                parser.assume(Event.END_OBJECT);
                                return object;
                            }
                        }
                        """);
    }


    @SuppressWarnings("unused") @Setter @Getter
    public static class StringContainerPrivateConstructor {
        private String instance = "Test String";

        private StringContainerPrivateConstructor() {
        }

        public static StringContainerPrivateConstructor getClassInstance() {
            return new StringContainerPrivateConstructor();
        }
    }

    @Test
    void shouldGenerateErrorWithoutPublicNoArgConstructor() {
        var messages = generator(StringContainerPrivateConstructor.class);
        then(messages).containsExactly(StringContainerPrivateConstructor.class.getName()
                                       + ": doesn't have a public no-args constructor");

        then(generated(StringContainerPrivateConstructor.class)).isEqualTo("""
                package com.github.t1.jsonbap.impl;
                
                import java.lang.reflect.Type;
                
                import javax.annotation.processing.Generated;
                
                import com.github.t1.jsonbap.runtime.FluentParser;
                
                import jakarta.json.bind.JsonbException;
                import jakarta.json.bind.serializer.DeserializationContext;
                import jakarta.json.bind.serializer.JsonbDeserializer;
                import jakarta.json.stream.JsonParser;
                import jakarta.json.stream.JsonParser.Event;
                
                @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                public class JsonbDeserializerGeneratorTest$StringContainerPrivateConstructor$$JsonbDeserializer implements JsonbDeserializer<JsonbDeserializerGeneratorTest$StringContainerPrivateConstructor> {
                
                    @Override
                    public JsonbDeserializerGeneratorTest$StringContainerPrivateConstructor deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
                        var parser = new FluentParser(jsonParser);
                        if (parser.is(Event.VALUE_NULL)) return null;
                        if (true) throw new JsonbException("doesn't have a public no-args constructor");
                        JsonbDeserializerGeneratorTest$StringContainerPrivateConstructor object = null;
                        parser.assume(Event.START_OBJECT);
                        while (parser.next().is(Event.KEY_NAME)) {
                            switch (parser.StringAndNext()) {
                                case "instance" -> parser.readString().ifPresent(value -> object.setInstance(value));
                            }
                        }
                        parser.assume(Event.END_OBJECT);
                        return object;
                    }
                }
                """);
    }


    @SuppressWarnings("unused")
    public static class StringContainerPublicStaticNestedClass {
        public NestedClass nestedClass = new NestedClass();

        @Setter @Getter
        public static class NestedClass {
            private String instance = "Test String";
        }
    }

    @Test
    void shouldGeneratedNestedClassField() {
        generate(StringContainerPublicStaticNestedClass.class);

        then(generated(StringContainerPublicStaticNestedClass.class)).isEqualTo("""
                package com.github.t1.jsonbap.impl;
                
                import java.lang.reflect.Type;
                
                import javax.annotation.processing.Generated;
                
                import com.github.t1.jsonbap.runtime.FluentParser;
                
                import jakarta.json.bind.serializer.DeserializationContext;
                import jakarta.json.bind.serializer.JsonbDeserializer;
                import jakarta.json.stream.JsonParser;
                import jakarta.json.stream.JsonParser.Event;
                
                @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                public class JsonbDeserializerGeneratorTest$StringContainerPublicStaticNestedClass$$JsonbDeserializer implements JsonbDeserializer<JsonbDeserializerGeneratorTest$StringContainerPublicStaticNestedClass> {
                
                    @Override
                    public JsonbDeserializerGeneratorTest$StringContainerPublicStaticNestedClass deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
                        var parser = new FluentParser(jsonParser);
                        if (parser.is(Event.VALUE_NULL)) return null;
                        var object = new JsonbDeserializerGeneratorTest$StringContainerPublicStaticNestedClass();
                        parser.assume(Event.START_OBJECT);
                        while (parser.next().is(Event.KEY_NAME)) {
                            switch (parser.StringAndNext()) {
                                case "nestedClass" -> object.nestedClass = ctx.deserialize(JsonbDeserializerGeneratorTest$StringContainerPublicStaticNestedClass$NestedClass.class, jsonParser);
                            }
                        }
                        parser.assume(Event.END_OBJECT);
                        return object;
                    }
                }
                """);
    }

    @SuppressWarnings("unused")
    public interface Container<T> {
        T getElement();

        void setElement(T instance);
    }

    @Setter @Getter
    public static class StringContainer implements Container<String> {
        private String element = "Test";
    }

    public static class StringContainerArraySerializer implements JsonbSerializer<StringContainer[]> {
        @Override
        public void serialize(StringContainer[] obj, JsonGenerator generator, SerializationContext ctx) {
            generator.writeStartArray();
            for (var container : obj) {
                ctx.serialize(container, generator);
            }
            generator.writeEnd();
        }
    }

    public static class StringContainerArrayDeserializer implements JsonbDeserializer<StringContainer[]> {
        public StringContainer[] deserialize(JsonParser parser, DeserializationContext ctx, Type type) {
            var containers = new ArrayList<StringContainer>();
            while (parser.hasNext()) {
                var event = parser.next();
                if (event == JsonParser.Event.START_OBJECT) {
                    containers.add(ctx.deserialize(new StringContainer() {}.getClass().getGenericSuperclass(), parser));
                }
                if (event == JsonParser.Event.END_OBJECT) {
                    break;
                }
            }

            return containers.toArray(new StringContainer[0]);
        }
    }

    @Getter @Setter
    public static class AnnotatedSerializerStringContainerArray implements Container<StringContainer[]> {
        @JsonbTypeSerializer(StringContainerArraySerializer.class)
        @JsonbTypeDeserializer(StringContainerArrayDeserializer.class)
        private StringContainer[] element;
    }

    @Test
    void shouldGenerateWithAnnotatedSerializerOfStringContainerArray() {
        generate(AnnotatedSerializerStringContainerArray.class);

        then(generated(AnnotatedSerializerStringContainerArray.class)).isEqualTo("""
                package com.github.t1.jsonbap.impl;
                
                import java.lang.reflect.Type;
                
                import javax.annotation.processing.Generated;
                
                import com.github.t1.jsonbap.runtime.FluentParser;
                
                import jakarta.json.bind.serializer.DeserializationContext;
                import jakarta.json.bind.serializer.JsonbDeserializer;
                import jakarta.json.stream.JsonParser;
                import jakarta.json.stream.JsonParser.Event;
                
                @Generated("com.github.t1.jsonbap.impl.JsonbAnnotationProcessor")
                public class JsonbDeserializerGeneratorTest$AnnotatedSerializerStringContainerArray$$JsonbDeserializer implements JsonbDeserializer<JsonbDeserializerGeneratorTest$AnnotatedSerializerStringContainerArray> {
                
                    @Override
                    public JsonbDeserializerGeneratorTest$AnnotatedSerializerStringContainerArray deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
                        var parser = new FluentParser(jsonParser);
                        if (parser.is(Event.VALUE_NULL)) return null;
                        var object = new JsonbDeserializerGeneratorTest$AnnotatedSerializerStringContainerArray();
                        parser.assume(Event.START_OBJECT);
                        while (parser.next().is(Event.KEY_NAME)) {
                            switch (parser.StringAndNext()) {
                                case "element" -> object.setElement(ctx.deserialize(JsonbDeserializerGeneratorTest$StringContainer[].class, jsonParser));
                            }
                        }
                        parser.assume(Event.END_OBJECT);
                        return object;
                    }
                }
                """);
    }
}
