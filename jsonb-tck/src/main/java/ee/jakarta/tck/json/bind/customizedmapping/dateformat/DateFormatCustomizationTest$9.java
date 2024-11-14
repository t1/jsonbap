package ee.jakarta.tck.json.bind.customizedmapping.dateformat;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.dateformat.model.customized.CustomizedPackageTypeOverrideDateContainer;

import java.util.Date;

@Bindable(deserializable = false)
public class DateFormatCustomizationTest$9 extends CustomizedPackageTypeOverrideDateContainer {
    public DateFormatCustomizationTest$9(DateFormatCustomizationTest ignoredUnused, Date instance) {
        setInstance(instance);
    }
}
