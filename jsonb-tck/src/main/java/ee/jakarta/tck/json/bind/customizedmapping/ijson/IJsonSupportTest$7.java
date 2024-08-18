package ee.jakarta.tck.json.bind.customizedmapping.ijson;

import ee.jakarta.tck.json.bind.customizedmapping.ijson.model.DurationContainer;

import java.time.Duration;

public class IJsonSupportTest$7 extends DurationContainer {
    public IJsonSupportTest$7(IJsonSupportTest ignoreUnused) {
        setInstance(Duration.ofDays(1).plus(Duration.ofHours(1)).plus(Duration.ofSeconds(1)));
    }
}
