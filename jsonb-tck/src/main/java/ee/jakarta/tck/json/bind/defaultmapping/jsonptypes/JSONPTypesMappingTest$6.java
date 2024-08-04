package ee.jakarta.tck.json.bind.defaultmapping.jsonptypes;

import ee.jakarta.tck.json.bind.defaultmapping.jsonptypes.model.JsonStructureContainer;
import jakarta.json.Json;
import jakarta.json.JsonStructure;

public class JSONPTypesMappingTest$6 extends JsonStructureContainer {
    {
        JsonStructure instance = Json.createArrayBuilder()
                .add(Json.createObjectBuilder().add("arrayInstance1", "Array Test String 1"))
                .add(Json.createObjectBuilder().add("arrayInstance2", "Array Test String 2"))
                .build();

        setInstance(instance);
    }
}
