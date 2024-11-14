package ee.jakarta.tck.json.bind.customizedmapping.numberformat;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.numberformat.model.FieldCustomizedDoubleContainer;

@Bindable(deserializable = false)
public class NumberFormatCustomizationTest$3 extends FieldCustomizedDoubleContainer {
    public NumberFormatCustomizationTest$3(NumberFormatCustomizationTest ignoredUnused) {
        setInstance(123456.789);
    }
}