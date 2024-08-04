package ee.jakarta.tck.json.bind.customizedmapping.ijson;

import ee.jakarta.tck.json.bind.customizedmapping.ijson.model.GregorianCalendarContainer;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class IJsonSupportTest$3 extends GregorianCalendarContainer {
    {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(1970, Calendar.JANUARY, 1, 0, 0, 0);
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        setInstance(gregorianCalendar);
    }
}
