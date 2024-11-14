package ee.jakarta.tck.json.bind.customizedmapping.propertynames;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.StringContainer;

import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.CASE_INSENSITIVE;

@Bindable(propertyNamingStrategy = CASE_INSENSITIVE, deserializable = false)
public class PropertyNameCustomizationTest$11 extends StringContainer {
    public PropertyNameCustomizationTest$11(PropertyNameCustomizationTest ignoreUnused) {
        setStringInstance("Test String");
    }
}
