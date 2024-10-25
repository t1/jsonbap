package ee.jakarta.tck.json.bind.defaultmapping.classes.model;

import com.github.t1.jsonbap.api.Bindable;

public class StringContainerProtectedStaticNestedClass {
    @Bindable @SuppressWarnings("unused")
    protected static class NestedClass {
        private String instance = "Test String";

        public String getInstance() {
            return instance;
        }

        public void setInstance(String instance) {
            this.instance = instance;
        }
    }
}
