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
            "import java.io.IOException;\n" +
            "import java.io.Writer;\n" +
            "\n" +
            "import javax.annotation.processing.Generated;\n" +
            "\n" +
            "import com.github.t1.jsonbap.api.JsonbWriter;\n" +
            // same package: "import com.github.t1.jsonbap.impl.ApJsonbProvider;\n" +
            "\n" +
            "@Generated(\"com.github.t1.jsonbap.impl.JsonbAnnotationProcessor\")\n" +
            "public class Person$$JsonbWriter implements JsonbWriter<Person> {\n" +
            "    @Override\n" +
            "    public void toJson(Person object, Writer out) throws IOException {\n" +
            "        char delim = '{';\n" +
            "        if (object.getAddress() != null) {\n" +
            "            out.append(delim).append(\"\\\"address\\\":\");\n" +
            "            ApJsonbProvider.jsonbWriterFor(object.getAddress()).toJson(object.getAddress(), out);\n" +
            "            delim = ',';\n" +
            "        }\n" +
            "        out.append(delim).append(\"\\\"age\\\":\").append(Integer.toString(object.getAge()));\n" +
            "        if (object.getFirstName() != null) {\n" +
            "            out.append(delim).append(\"\\\"firstName\\\":\\\"\").append(object.getFirstName()).append('\"');\n" +
            "            delim = ',';\n" +
            "        }\n" +
            "        if (object.getLastName() != null) {\n" +
            "            out.append(delim).append(\"\\\"lastName\\\":\\\"\").append(object.getLastName()).append('\"');\n" +
            "            delim = ',';\n" +
            "        }\n" +
            "        if (object.getRoles() != null) {\n" +
            "            out.append(delim).append(\"\\\"roles\\\":[\");\n" +
            "            var first = true;\n" +
            "            for (Object item : object.getRoles()) {\n" +
            "                if (item != null) {\n" +
            "                    if (!first) out.append(',');\n" +
            "                    ApJsonbProvider.jsonbWriterFor(item).toJson(item, out);\n" +
            "                    first = false;\n" +
            "                }\n" +
            "            }\n" +
            "            out.append(\"]\");\n" +
            "            delim = ',';\n" +
            "        }\n" +
            "        out.append(\"}\");\n" +
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
            "import java.io.IOException;\n" +
            "import java.io.Writer;\n" +
            "\n" +
            "import javax.annotation.processing.Generated;\n" +
            "\n" +
            "import com.github.t1.jsonbap.api.JsonbWriter;\n" +
            "\n" +
            "@Generated(\"com.github.t1.jsonbap.impl.JsonbAnnotationProcessor\")\n" +
            "public class Address$$JsonbWriter implements JsonbWriter<Address> {\n" +
            "    @Override\n" +
            "    public void toJson(Address object, Writer out) throws IOException {\n" +
            "        char delim = '{';\n" +
            "        if (object.getCity() != null) {\n" +
            "            out.append(delim).append(\"\\\"city\\\":\\\"\").append(object.getCity()).append('\"');\n" +
            "            delim = ',';\n" +
            "        }\n" +
            "        if (object.getCountry() != null) {\n" +
            "            out.append(delim).append(\"\\\"country\\\":\\\"\").append(object.getCountry()).append('\"');\n" +
            "            delim = ',';\n" +
            "        }\n" +
            "        if (object.getState() != null) {\n" +
            "            out.append(delim).append(\"\\\"state\\\":\\\"\").append(object.getState()).append('\"');\n" +
            "            delim = ',';\n" +
            "        }\n" +
            "        if (object.getStreet() != null) {\n" +
            "            out.append(delim).append(\"\\\"street\\\":\\\"\").append(object.getStreet()).append('\"');\n" +
            "            delim = ',';\n" +
            "        }\n" +
            "        if (object.getZip() != null) {\n" +
            "            out.append(delim).append(\"\\\"zip\\\":\").append(object.getZip().toString());\n" +
            "            delim = ',';\n" +
            "        }\n" +
            "        out.append(\"}\");\n" +
            "    }\n" +
            "\n" +
            "}\n");
    }
}
