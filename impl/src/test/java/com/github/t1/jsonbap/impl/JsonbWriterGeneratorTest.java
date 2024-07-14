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
                "package com.github.t1.jsonbap.impl;\n" +
                "\n" +
                "import javax.annotation.processing.Generated;\n" +
                "\n" +
                "import com.github.t1.jsonbap.api.JsonbWriter;\n" +
                "\n" +
                "import jakarta.json.stream.JsonGenerator;\n" +
                "\n" +
                "@Generated(\"com.github.t1.jsonbap.impl.JsonbAnnotationProcessor\")\n" +
                "public class Person$$JsonbWriter implements JsonbWriter<Person, JsonGeneratorContext> {\n" +
                "    @Override\n" +
                "    public void toJson(Person object, JsonGenerator out, JsonGeneratorContext context) {\n" +
                "        out.writeStartObject();\n" +
                "        if (object.getAddress() != null) {\n" +
                "            out.writeKey(\"address\");\n" +
                "            ApJsonbProvider.jsonbWriterFor(object.getAddress()).toJson(object.getAddress(), out, context);\n" +
                "        }\n" +
                "        out.write(\"age\", object.getAge());\n" +
                "        if (object.getFirstName() != null) {\n" +
                "            out.write(\"firstName\", object.getFirstName());\n" +
                "        } else if (context.writeNullValues()) {\n" +
                "            out.writeNull(\"firstName\");\n" +
                "        }\n" +
                "        if (object.getLastName() != null) {\n" +
                "            out.write(\"lastName\", object.getLastName());\n" +
                "        } else if (context.writeNullValues()) {\n" +
                "            out.writeNull(\"lastName\");\n" +
                "        }\n" +
                "        if (object.getRoles() != null) {\n" +
                "            out.writeKey(\"roles\");\n" +
                "            ApJsonbProvider.jsonbWriterFor(object.getRoles()).toJson(object.getRoles(), out, context);\n" +
                "        }\n" +
                "        out.writeEnd();\n" +
                "    }\n" +
                "\n" +
                "}\n");
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
                "package com.github.t1.jsonbap.impl;\n" +
                "\n" +
                "import javax.annotation.processing.Generated;\n" +
                "\n" +
                "import com.github.t1.jsonbap.api.JsonbWriter;\n" +
                "\n" +
                "import jakarta.json.stream.JsonGenerator;\n" +
                "\n" +
                "@Generated(\"com.github.t1.jsonbap.impl.JsonbAnnotationProcessor\")\n" +
                "public class Address$$JsonbWriter implements JsonbWriter<Address, JsonGeneratorContext> {\n" +
                "    @Override\n" +
                "    public void toJson(Address object, JsonGenerator out, JsonGeneratorContext context) {\n" +
                "        out.writeStartObject();\n" +
                "        if (object.getCity() != null) {\n" +
                "            out.write(\"city\", object.getCity());\n" +
                "        } else if (context.writeNullValues()) {\n" +
                "            out.writeNull(\"city\");\n" +
                "        }\n" +
                "        if (object.getCountry() != null) {\n" +
                "            out.write(\"country\", object.getCountry());\n" +
                "        } else if (context.writeNullValues()) {\n" +
                "            out.writeNull(\"country\");\n" +
                "        }\n" +
                "        if (object.getState() != null) {\n" +
                "            out.write(\"state\", object.getState());\n" +
                "        } else if (context.writeNullValues()) {\n" +
                "            out.writeNull(\"state\");\n" +
                "        }\n" +
                "        if (object.getStreet() != null) {\n" +
                "            out.write(\"street\", object.getStreet());\n" +
                "        } else if (context.writeNullValues()) {\n" +
                "            out.writeNull(\"street\");\n" +
                "        }\n" +
                "        if (object.getZip() != null) {\n" +
                "            out.write(\"zip\", object.getZip());\n" +
                "        } else if (context.writeNullValues()) {\n" +
                "            out.writeNull(\"zip\");\n" +
                "        }\n" +
                "        out.writeEnd();\n" +
                "    }\n" +
                "\n" +
                "}\n");
    }
}
