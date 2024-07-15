package test;

import com.github.t1.jsonbap.test.Address;
import com.github.t1.jsonbap.test.Person;
import jakarta.json.Json;
import jakarta.json.stream.JsonGenerator;

import java.io.StringWriter;
import java.util.List;

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
        out.write("age", object.getAge());
        out.write("averageScore", object.getAverageScore());
        if (object.getFirstName() != null) {
            out.write("firstName", object.getFirstName());
        }
        if (object.getLastName() != null) {
            out.write("lastName", object.getLastName());
        }
        out.write("member", object.getMember());
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
}
