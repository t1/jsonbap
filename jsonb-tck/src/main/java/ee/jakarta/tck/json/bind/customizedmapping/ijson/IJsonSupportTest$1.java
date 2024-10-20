package ee.jakarta.tck.json.bind.customizedmapping.ijson;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.ijson.model.DateContainer;

import java.util.Calendar;

@Bindable
public class IJsonSupportTest$1 extends DateContainer {
    public IJsonSupportTest$1(IJsonSupportTest ignoreUnused, Calendar instance) {
        setInstance(instance.getTime());
    }
}
