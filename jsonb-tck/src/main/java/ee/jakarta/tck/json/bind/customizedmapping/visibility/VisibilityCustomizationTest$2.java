package ee.jakarta.tck.json.bind.customizedmapping.visibility;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.customizedmapping.visibility.model.CustomVisibilityAnnotatedContainer;

@Bindable(deserializable = false)
public class VisibilityCustomizationTest$2 extends CustomVisibilityAnnotatedContainer {
    public VisibilityCustomizationTest$2(VisibilityCustomizationTest ignoredUnused) {
        setStringInstance("Test String");
    }
}
