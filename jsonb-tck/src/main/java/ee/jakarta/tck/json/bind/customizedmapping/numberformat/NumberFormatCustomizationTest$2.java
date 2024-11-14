package ee.jakarta.tck.json.bind.customizedmapping.numberformat;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.numberformat.model.TypeCustomizedDoubleContainer;

@Bindable(deserializable = false)
public class NumberFormatCustomizationTest$2 extends TypeCustomizedDoubleContainer {
    public NumberFormatCustomizationTest$2(NumberFormatCustomizationTest ignoredUnused) {
        setInstance(123456.789);
    }
}
