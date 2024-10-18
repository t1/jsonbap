package test;

import com.github.t1.jsonbap.test.Address;
import com.github.t1.jsonbap.test.Cat;
import com.github.t1.jsonbap.test.Dog;
import com.github.t1.jsonbap.test.Person;
import com.github.t1.jsonbap.test.Pet;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

public class StringWriterIT extends AbstractJsonIT {
    @SneakyThrows(IOException.class)
    @Override String toJson(Object object) {
        @SuppressWarnings("unchecked")
        List<Person> person = (List<Person>) object;
        var out = new StringWriter();
        toJson(person, out);
        return out.toString();
    }

    private static void toJson(List<Person> object, Writer out) throws IOException {
        out.append('[');
        var first = true;
        for (Person item : object) {
            if (item != null) {
                if (!first) out.append(',');
                toJson(item, out);
                first = false;
            }
        }
        out.append(']');
    }

    private static void toJson(Person object, Writer out) throws IOException {
        char delim = '{';
        if (object.getAddress() != null) {
            out.append(delim).append("\"address\":");
            toJson(object.getAddress(), out);
            delim = ',';
        }
        if (object.getFormerAddress() != null) {
            out.append(delim).append("\"formerAddress\":");
            toJson(object.getFormerAddress(), out);
            delim = ',';
        }
        out.append(delim).append("\"age\":").append(Integer.toString(object.getAge()));
        out.append(delim).append("\"averageScore\":").append(Double.toString(object.getAverageScore()));
        if (object.getFirstName() != null) {
            out.append(delim).append("\"firstName\":\"").append(object.getFirstName()).append('"');
            delim = ',';
        }
        if (object.getLastName() != null) {
            out.append(delim).append("\"lastName\":\"").append(object.getLastName()).append('"');
            delim = ',';
        }
        out.append(delim).append("\"member\":").append(Boolean.toString(object.getMember()));
        if (object.getPets() != null) {
            out.append(delim).append("\"pets\":[");
            var first = true;
            for (Pet item : object.getPets()) {
                if (item != null) {
                    if (!first) out.append(',');
                    toJson(item, out);
                    first = false;
                }
            }
            out.append("]");
            delim = ',';
        }
        out.append(delim).append("\"registrationTimestamp\":").append(Long.toString(object.getRegistrationTimestamp()));
        if (object.getRoles() != null) {
            out.append(delim).append("\"roles\":[");
            var first = true;
            for (String item : object.getRoles()) {
                if (item != null) {
                    if (!first) out.append(',');
                    out.append('"').append(item).append('"');
                    first = false;
                }
            }
            out.append("]");
            delim = ',';
        }
        out.append("}");
    }

    private static void toJson(Address object, Writer out) throws IOException {
        char delim = '{';
        if (object.getCity() != null) {
            out.append(delim).append("\"city\":\"").append(object.getCity()).append('"');
            delim = ',';
        }
        if (object.getCountry() != null) {
            out.append(delim).append("\"country\":\"").append(object.getCountry()).append('"');
            delim = ',';
        }
        if (object.getState() != null) {
            out.append(delim).append("\"state\":\"").append(object.getState()).append('"');
            delim = ',';
        }
        if (object.getStreet() != null) {
            out.append(delim).append("\"street\":\"").append(object.getStreet()).append('"');
            delim = ',';
        }
        if (object.getZip() != null) {
            out.append(delim).append("\"zip\":").append(object.getZip().toString());
            delim = ',';
        }
        out.append("}");
    }

    private static void toJson(Pet object, Writer out) throws IOException {
        if (object instanceof Cat cat) toJson(cat, out);
        else if (object instanceof Dog dog) toJson(dog, out);
        else throw new IllegalArgumentException("unknown type: " + object);
    }

    private static void toJson(Cat object, Writer out) throws IOException {
        char delim = '{';
        out.append(delim).append("\"@type\":\"cat\"");
        delim = ',';
        out.append(delim).append("\"isCat\":true");
        if (object.getName() != null) {
            out.append(delim).append("\"name\":\"").append(object.getName()).append('"');
            delim = ',';
        }
        out.append("}");
    }

    private static void toJson(Dog object, Writer out) throws IOException {
        char delim = '{';
        out.append(delim).append("\"@type\":\"dog\"");
        delim = ',';
        out.append(delim).append("\"isDog\":true");
        if (object.getName() != null) {
            out.append(delim).append("\"name\":\"").append(object.getName()).append('"');
            delim = ',';
        }
        out.append("}");
    }
}
