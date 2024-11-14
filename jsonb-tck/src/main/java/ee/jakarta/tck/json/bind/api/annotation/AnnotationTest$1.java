package ee.jakarta.tck.json.bind.api.annotation;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.api.model.SimpleAnnotatedAdaptedContainer;

@Bindable(deserializable = false)
public class AnnotationTest$1 extends SimpleAnnotatedAdaptedContainer {
    public AnnotationTest$1(AnnotationTest ignoreUnused) {
        setInstance(new AnnotationTest$1$1(this));
    }
}
