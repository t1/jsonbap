package ee.jakarta.tck.json.bind.customizedmapping.ijson;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.ijson.model.DurationContainer;

import java.time.Duration;

@Bindable
public class IJsonSupportTest$7 extends DurationContainer {
    public IJsonSupportTest$7(IJsonSupportTest ignoreUnused) {
        setInstance(Duration.ofDays(1).plus(Duration.ofHours(1)).plus(Duration.ofSeconds(1)));
    }
}
