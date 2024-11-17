package com.github.t1.jsonbap.impl;

import com.github.t1.exap.ExtendedAbstractProcessor;
import com.github.t1.exap.Round;
import com.github.t1.exap.SupportedAnnotationClasses;
import com.github.t1.exap.insight.SourceAlreadyExistsException;
import com.github.t1.exap.insight.Type;
import com.github.t1.jsonbap.api.Bindable;

import java.util.function.Function;

@SupportedAnnotationClasses({Bindable.class})
public class JsonbAnnotationProcessor extends ExtendedAbstractProcessor {
    private JsonbapConfig config;

    @Override public boolean process(Round round) {
        this.config = new JsonbapConfig(processingEnv);
        if (round.number() == 0) note("jsonbap config " + config);
        round.typesAnnotatedWith(Bindable.class).forEach(this::process);
        return false;
    }

    private void process(Type type) {
        if (is(type, Bindable::serializable)) generateSerializerFor(type);
        if (is(type, Bindable::deserializable)) generateDeserializerFor(type);

        type.annotationWrapper(Bindable.class)
                .ifPresent(bindable -> bindable.getTypeProperties("value")
                        .forEach(this::process));
    }

    private static boolean is(Type type, Function<Bindable, Boolean> serializable) {
        return type.annotation(Bindable.class).map(serializable).orElse(false);
    }

    private void generateSerializerFor(Type type) {
        var generator = new JsonbSerializerGenerator(config, type);
        type.note("generate " + generator.className());
        try (var typeGenerator = type.getPackage().openTypeGenerator(generator.className())) {
            generator.generate(typeGenerator);
        } catch (SourceAlreadyExistsException e) {
            type.warning("serializer already exists: " + e.getSourceName());
        }
    }

    private void generateDeserializerFor(Type type) {
        var generator = new JsonbDeserializerGenerator(config, type);
        type.note("generate " + generator.className());
        try (var typeGenerator = type.getPackage().openTypeGenerator(generator.className())) {
            generator.generate(typeGenerator);
        } catch (SourceAlreadyExistsException e) {
            type.warning("deserializer already exists: " + e.getSourceName());
        } catch (RuntimeException e) {
            throw new RuntimeException("can't generate deserializer for " + type, e);
        }
    }
}
