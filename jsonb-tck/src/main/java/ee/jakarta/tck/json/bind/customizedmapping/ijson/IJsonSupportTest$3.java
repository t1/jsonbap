package ee.jakarta.tck.json.bind.customizedmapping.ijson;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.ijson.model.GregorianCalendarContainer;

import java.util.GregorianCalendar;

@Bindable(deserializable = false)
public class IJsonSupportTest$3 extends GregorianCalendarContainer {
    public IJsonSupportTest$3(IJsonSupportTest ignoreUnused, GregorianCalendar gregorianCalendar) {
        setInstance(gregorianCalendar);
    }
}
