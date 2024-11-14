package ee.jakarta.tck.json.bind.customizedmapping.dateformat;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.dateformat.model.AnnotatedFieldDateContainer;

import java.util.Date;

@Bindable(deserializable = false)
public class DateFormatCustomizationTest$4 extends AnnotatedFieldDateContainer {
    public DateFormatCustomizationTest$4(DateFormatCustomizationTest ignoredUnused, Date instance) {
        setInstance(instance);
    }
}
