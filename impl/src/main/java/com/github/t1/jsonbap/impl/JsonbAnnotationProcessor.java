package com.github.t1.jsonbap.impl;

import com.github.t1.exap.ExtendedAbstractProcessor;
import com.github.t1.exap.Round;
import com.github.t1.exap.SupportedAnnotationClasses;
import com.github.t1.exap.insight.SourceAlreadyExistsException;
import com.github.t1.exap.insight.Type;
import com.github.t1.jsonbap.api.Bindable;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_17;

@Slf4j
@SupportedSourceVersion(RELEASE_17)
@SupportedAnnotationClasses({Bindable.class})
public class JsonbAnnotationProcessor extends ExtendedAbstractProcessor {
    @Override public boolean process(Round round) {
        round.typesAnnotatedWith(Bindable.class).forEach(this::process);
        return false;
    }

    private void process(Type type) {
        var bindable = type.annotationWrapper(Bindable.class);
        if (bindable.isEmpty() || bindable.get().getBooleanProperty("serializable")) {
            generateSerializerFor(type);
        }

        bindable.stream()
                .flatMap(b -> b.getTypeProperties("value").stream())
                .forEach(this::process);
    }

    private static void generateSerializerFor(Type type) {
        var generator = new JsonbSerializerGenerator(type);
        type.warning("generate " + generator.className());
        try (var typeGenerator = type.getPackage().openTypeGenerator(generator.className())) {
            generator.generate(typeGenerator);
        } catch (SourceAlreadyExistsException e) {
            type.warning("serializer already exists: " + e.getSourceName());
        }
    }
}
