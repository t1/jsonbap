package ee.jakarta.tck.json.bind.customizedmapping.ijson;

import ee.jakarta.tck.json.bind.customizedmapping.ijson.model.DateContainer;

import java.util.Calendar;
import java.util.TimeZone;

public class IJsonSupportTest$1 extends DateContainer {
    {
        final Calendar instance = Calendar.getInstance();
        instance.clear();
        instance.set(1970, Calendar.JANUARY, 1);
        instance.setTimeZone(TimeZone.getTimeZone("UTC"));
        setInstance(instance.getTime());
    }
}
