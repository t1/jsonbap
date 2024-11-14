package ee.jakarta.tck.json.bind.customizedmapping.propertynames;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.StringContainer;

import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.LOWER_CASE_WITH_DASHES;

@Bindable(propertyNamingStrategy = LOWER_CASE_WITH_DASHES, deserializable = false)
public class PropertyNameCustomizationTest$7 extends StringContainer {
    public PropertyNameCustomizationTest$7(PropertyNameCustomizationTest ignoreUnused) {
        setStringInstance("Test String");
    }
}
