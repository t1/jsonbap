package ee.jakarta.tck.json.bind.customizedmapping.ijson;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.ijson.model.CalendarContainer;

import java.util.Calendar;

@Bindable
public class IJsonSupportTest$2 extends CalendarContainer {
    public IJsonSupportTest$2(IJsonSupportTest ignoreUnused, Calendar calendarProperty) {
        setInstance(calendarProperty);
    }
}
