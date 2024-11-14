package ee.jakarta.tck.json.bind.customizedmapping.dateformat;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.dateformat.model.customized.CustomizedPackageTypeOverrideFieldOverrideDateContainer;

import java.util.Date;

@Bindable(deserializable = false)
public class DateFormatCustomizationTest$10 extends CustomizedPackageTypeOverrideFieldOverrideDateContainer {
    public DateFormatCustomizationTest$10(DateFormatCustomizationTest ignoredUnused, Date instance) {
        setInstance(instance);
    }
}
