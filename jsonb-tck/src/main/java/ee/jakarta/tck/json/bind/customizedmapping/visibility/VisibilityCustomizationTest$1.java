package ee.jakarta.tck.json.bind.customizedmapping.visibility;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.visibility.model.SimpleContainer;

@Bindable
public class VisibilityCustomizationTest$1 extends SimpleContainer {
    public VisibilityCustomizationTest$1(VisibilityCustomizationTest ignoredUnused) {
        setStringInstance("Test String");
    }
}
