package com.github.t1.jsonbap.impl;

import com.github.t1.exap.generator.TypeExpressionGenerator;
import com.github.t1.exap.generator.TypeGenerator;
import com.github.t1.exap.reflection.ReflectionProcessingEnvironment;
import com.github.t1.exap.reflection.Type;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

import javax.annotation.processing.Generated;
import java.util.List;

import static java.util.stream.Collectors.toList;

class JsonbSerializerGenerator {
    private static Type type(Class<?> klass) {
        return ReflectionProcessingEnvironment.ENV.type(klass);
    }

    private final Type type;

    public JsonbSerializerGenerator(Type type) {
        this.type = type;
    }

    public String className() {
        return type.getSimpleName() + "$$JsonbSerializer";
    }

    public void generate(TypeGenerator typeGenerator) {
        typeGenerator.addImport(type(JsonGeneratorContext.class));
        typeGenerator.annotation(type(Generated.class))
            .set("value", JsonbAnnotationProcessor.class.getName());
        typeGenerator.addImplements(new TypeExpressionGenerator(typeGenerator, type(JsonbSerializer.class))
                .withTypeArg(type));

        var toJson = typeGenerator.addMethod("serialize");
        toJson.annotation(type(Override.class));
        toJson.addParameter("object").type(new TypeExpressionGenerator(typeGenerator, type));
        toJson.addParameter("out").type(new TypeExpressionGenerator(typeGenerator, type(JsonGenerator.class)));
        toJson.addParameter("context").type(new TypeExpressionGenerator(typeGenerator, type(SerializationContext.class)));
        toJson.body(body());
    }

    private String body() {
        var body = new StringBuilder();
        body.append("out.writeStartObject();\n");
        for (var property : properties()) {
            property.write(body);
        }
        body.append("        out.writeEnd();");
        return body.toString();
    }

    private List<Property> properties() {
        return type.getAllMethods().stream()
            .filter(GetterProperty::isGetter)
            .map(GetterProperty::of)
            .sorted()
            .collect(toList());
    }
}
