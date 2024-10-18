package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeExpressionGenerator;
import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.insight.Type;
import com.github.t1.exap.reflection.ReflectionProcessingEnvironment;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static com.github.t1.exap.generator.Visibility.PUBLIC;
import static com.github.t1.jsonbap.impl.FieldProperty.fieldProperties;
import static com.github.t1.jsonbap.impl.GetterProperty.getterProperties;
import static com.github.t1.jsonbap.impl.TypeProperty.typeProperties;

class JsonbSerializerGenerator {
    private static Type type(Class<?> klass) {
        return ReflectionProcessingEnvironment.ENV.type(klass);
    }

    private final Type type;

    public JsonbSerializerGenerator(Type type) {
        this.type = type;
    }

    public String className() {
        return type.getRelativeName().replace('.', '$') + "$$JsonbSerializer";
    }

    public void generate(TypeGenerator typeGenerator) {
        typeGenerator.annotation(type(Generated.class))
                .set("value", JsonbAnnotationProcessor.class.getName());
        typeGenerator.addImplements(new TypeExpressionGenerator(typeGenerator, type(JsonbSerializer.class))
                .withTypeArg(type));

        var toJson = typeGenerator.addMethod(PUBLIC, "serialize");
        toJson.annotation(type(Override.class));
        toJson.addParameter("object").type(new TypeExpressionGenerator(typeGenerator, type));
        toJson.addParameter("out").type(new TypeExpressionGenerator(typeGenerator, type(JsonGenerator.class)));
        toJson.addParameter("context").type(new TypeExpressionGenerator(typeGenerator, type(SerializationContext.class)));
        toJson.body(body(typeGenerator));
    }

    private String body(TypeGenerator typeGenerator) {
        var body = new StringBuilder();
        body.append("out.writeStartObject();\n");
        properties().forEach(property -> property.write(typeGenerator, body));
        body.append("        out.writeEnd();");
        return body.toString();
    }

    private List<Property<?>> properties() {
        // even before the sorting: the order of these property streams is relevant, as we merge only in one direction
        return Stream.concat(
                        Stream.concat(
                                typeProperties(type),
                                Stream.empty()), // just for symmetry
                        Stream.concat(
                                fieldProperties(type),
                                getterProperties(type)))
                .sorted()
                .collect(propertiesMerger());
    }

    private Collector<Property<?>, Map<String, Property<?>>, List<Property<?>>> propertiesMerger() {
        return Collector.of(
                LinkedHashMap::new,
                (m, p) -> m.merge(p.name(), p, this::merge),
                (l, r) -> {l.putAll(r); return l;},
                m -> List.copyOf(m.values())
        );
    }

    // we need this indirection to work around the type wildcards
    @SuppressWarnings({"unchecked", "rawtypes"})
    private Property<?> merge(Property<?> property, Property<?> that) {return property.merge((Property) that);}
}
