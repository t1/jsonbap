package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.ArrayListContainer;

import java.util.ArrayList;

public class CollectionsMappingTest$29 extends ArrayListContainer {
    {
        ArrayList<String> instance = new ArrayList<>() {{
            add("Test 1");
            add("Test 2");
        }};
        setInstance(instance);

    }
}
