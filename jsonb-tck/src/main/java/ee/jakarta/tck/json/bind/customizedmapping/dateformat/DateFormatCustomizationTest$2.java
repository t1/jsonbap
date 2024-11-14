package ee.jakarta.tck.json.bind.customizedmapping.dateformat;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.dateformat.model.customized.CustomizedPackageDateContainer;

import java.util.Date;

@Bindable(deserializable = false)
public class DateFormatCustomizationTest$2 extends CustomizedPackageDateContainer {
    public DateFormatCustomizationTest$2(DateFormatCustomizationTest ignoredUnused, Date instance) {
        setInstance(instance);
    }
}
