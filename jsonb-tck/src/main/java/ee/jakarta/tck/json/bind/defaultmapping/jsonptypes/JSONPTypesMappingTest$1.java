package ee.jakarta.tck.json.bind.defaultmapping.jsonptypes;

import ee.jakarta.tck.json.bind.defaultmapping.jsonptypes.model.JsonObjectContainer;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

public class JSONPTypesMappingTest$1 extends JsonObjectContainer {
    {
        JsonObject instance = Json.createObjectBuilder()
                .add("jsonObjectInstance", Json.createObjectBuilder().add("innerInstance", "Inner Test String"))
                .add("jsonArrayInstance",
                        Json.createArrayBuilder()
                                .add(Json.createObjectBuilder().add("arrayInstance1", "Array Test String 1"))
                                .add(Json.createObjectBuilder().add("arrayInstance2", "Array Test String 2")))
                .add("jsonStringInstance", "Test String")
                .add("jsonNumberInstance", Integer.MAX_VALUE)
                .add("jsonTrueInstance", JsonValue.TRUE)
                .add("jsonFalseInstance", JsonValue.FALSE)
                .add("jsonNullInstance", JsonValue.NULL).build();

        setInstance(instance);
    }
}
