package com.github.t1.jsonbap.impl;

import com.github.t1.exap.ExtendedAbstractProcessor;
import com.github.t1.exap.Round;
import com.github.t1.exap.SupportedAnnotationClasses;
import com.github.t1.exap.insight.SourceAlreadyExistsException;
import com.github.t1.exap.insight.Type;
import com.github.t1.jsonbap.api.Bindable;
import lombok.extern.slf4j.Slf4j;

@SupportedAnnotationClasses({Bindable.class})
@Slf4j
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

        bindable.ifPresent(bindable_ -> bindable_.getTypeProperties("value")
                .forEach(this::process));
    }

    private static void generateSerializerFor(Type type) {
        var generator = new JsonbSerializerGenerator(type);
        log.info("generate {}", generator.className());
        type.warning("generate " + generator.className()); // TODO use note
        try (var typeGenerator = type.getPackage().openTypeGenerator(generator.className())) {
            generator.generate(typeGenerator);
        } catch (SourceAlreadyExistsException e) {
            type.warning("serializer already exists: " + e.getSourceName());
        }
    }
}
