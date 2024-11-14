package ee.jakarta.tck.json.bind.customizedmapping.numberformat;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.numberformat.model.customized.PackageCustomizedDoubleContainer;

@Bindable(deserializable = false)
public class NumberFormatCustomizationTest$1 extends PackageCustomizedDoubleContainer {
    public NumberFormatCustomizationTest$1(NumberFormatCustomizationTest ignoredUnused) {
        setInstance(123456.789);
    }
}
