package ee.jakarta.tck.json.bind.customizedmapping.numberformat;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.numberformat.model.TypeCustomizedFieldOverriddenDoubleContainer;

@Bindable(deserializable = false)
public class NumberFormatCustomizationTest$6 extends TypeCustomizedFieldOverriddenDoubleContainer {
    public NumberFormatCustomizationTest$6(NumberFormatCustomizationTest ignoredUnused) {
        setInstance(123456.789);
    }
}
