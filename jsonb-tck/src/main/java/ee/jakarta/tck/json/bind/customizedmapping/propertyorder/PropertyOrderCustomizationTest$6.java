package ee.jakarta.tck.json.bind.customizedmapping.propertyorder;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.propertyorder.model.PartialOrderContainer;

@Bindable(deserializable = false)
public class PropertyOrderCustomizationTest$6 extends PartialOrderContainer {
    public PropertyOrderCustomizationTest$6(PropertyOrderCustomizationTest ignoreUnused) {
        setStringInstance("Test String");
    }
}
