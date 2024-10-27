package test;

import com.github.t1.jsonbap.test.Address;
import com.github.t1.jsonbap.test.Cat;
import com.github.t1.jsonbap.test.Dog;
import com.github.t1.jsonbap.test.Person;
import com.github.t1.jsonbap.test.Pet;
import jakarta.json.Json;
import jakarta.json.stream.JsonGenerator;

import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class JsonPIT extends AbstractJsonIT {
    @Override String toJson(Object object) {
        @SuppressWarnings("unchecked")
        List<Person> person = (List<Person>) object;
        var out = new StringWriter();
        var generator = Json.createGenerator(out);
        toJson(person, generator);
        generator.flush();
        return out.toString();
    }

    private static void toJson(List<Person> object, JsonGenerator out) {
        out.writeStartArray();
        for (Person item : object) {
            if (item != null) {
                toJson(item, out);
            }
        }
        out.writeEnd();
    }

    private static void toJson(Person object, JsonGenerator out) {
        out.writeStartObject();
        if (object.getAddress() != null) {
            out.writeStartObject("address");
            toJson(object.getAddress(), out);
            out.writeEnd();
        }
        if (object.getFormerAddress() != null) {
            out.writeStartObject("formerAddress");
            toJson(object.getFormerAddress(), out);
            out.writeEnd();
        }
        out.write("age", object.getAge());
        out.write("averageScore", object.getAverageScore());
        if (object.getFirstName() != null) {
            out.write("firstName", object.getFirstName());
        }
        out.write("income", Optional.ofNullable(object.getIncome())
                .map(NumberFormat.getInstance(Locale.of("fr"))::format)
                .orElse(null));
        if (object.getLastName() != null) {
            out.write("lastName", object.getLastName());
        }
        out.write("member", object.isMember());
        if (object.getPets() != null) {
            out.writeStartArray("pets");
            for (Pet pet : object.getPets()) {
                if (pet != null) {
                    toJson(pet, out);
                }
            }
            out.writeEnd();
        }
        out.write("registrationTimestamp", object.getRegistrationTimestamp());
        if (object.getRoles() != null) {
            out.writeStartArray("roles");
            for (String role : object.getRoles()) {
                if (role != null) {
                    out.write(role);
                }
            }
            out.writeEnd();
        }
        out.writeEnd();
    }

    private static void toJson(Address object, JsonGenerator out) {
        if (object.getCity() != null) {
            out.write("city", object.getCity());
        }
        if (object.getCountry() != null) {
            out.write("country", object.getCountry());
        }
        if (object.getState() != null) {
            out.write("state", object.getState());
        }
        if (object.getStreet() != null) {
            out.write("street", object.getStreet());
        }
        if (object.getZip() != null) {
            out.write("zip", object.getZip());
        }
    }

    private static void toJson(Pet object, JsonGenerator out) {
        if (object instanceof Cat cat) toJson(cat, out);
        else if (object instanceof Dog dog) toJson(dog, out);
        else throw new IllegalArgumentException("unknown type: " + object);
    }

    private static void toJson(Cat object, JsonGenerator out) {
        out.writeStartObject();
        out.write("@type", "cat");
        out.write("isCat", object.getIsCat());
        if (object.getName() != null) {
            out.write("name", object.getName());
        }
        out.writeEnd();
    }

    private static void toJson(Dog object, JsonGenerator out) {
        out.writeStartObject();
        out.write("@type", "dog");
        out.write("isDog", object.getIsDog());
        if (object.getName() != null) {
            out.write("name", object.getName());
        }
        out.writeEnd();
    }
}
