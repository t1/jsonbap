package ee.jakarta.tck.json.bind.customizedmapping.propertyorder;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.propertyorder.model.SimpleOrderContainer;

@Bindable(deserializable = false)
public class PropertyOrderCustomizationTest$2 extends SimpleOrderContainer {
    public PropertyOrderCustomizationTest$2(PropertyOrderCustomizationTest ignoreUnused) {
        setStringInstance("Test String");
    }
}
