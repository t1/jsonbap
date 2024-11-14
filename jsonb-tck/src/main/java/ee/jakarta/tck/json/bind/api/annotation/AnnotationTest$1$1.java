package ee.jakarta.tck.json.bind.api.annotation;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.api.model.SimpleContainer;

@Bindable(deserializable = false)
public class AnnotationTest$1$1 extends SimpleContainer {
    public AnnotationTest$1$1(AnnotationTest$1 ignoreUnused) {
        setInstance("Test String");
    }
}
