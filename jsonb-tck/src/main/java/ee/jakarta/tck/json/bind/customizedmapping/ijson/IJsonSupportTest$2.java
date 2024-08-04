package ee.jakarta.tck.json.bind.customizedmapping.ijson;

import ee.jakarta.tck.json.bind.customizedmapping.ijson.model.CalendarContainer;

import java.util.Calendar;
import java.util.TimeZone;

public class IJsonSupportTest$2 extends CalendarContainer {
    {
        Calendar calendarProperty = Calendar.getInstance();
        calendarProperty.clear();
        calendarProperty.set(1970, Calendar.JANUARY, 1);
        calendarProperty.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        setInstance(calendarProperty);
    }
}
