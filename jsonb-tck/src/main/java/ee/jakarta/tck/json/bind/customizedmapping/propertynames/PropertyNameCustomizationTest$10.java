package ee.jakarta.tck.json.bind.customizedmapping.propertynames;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.StringContainer;

import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.UPPER_CAMEL_CASE_WITH_SPACES;

@Bindable(propertyNamingStrategy = UPPER_CAMEL_CASE_WITH_SPACES)
public class PropertyNameCustomizationTest$10 extends StringContainer {
    public PropertyNameCustomizationTest$10(PropertyNameCustomizationTest ignoreUnused) {
        setStringInstance("Test String");
    }
}
