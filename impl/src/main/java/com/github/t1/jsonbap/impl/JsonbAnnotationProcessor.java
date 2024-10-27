package com.github.t1.jsonbap.impl;

import com.github.t1.exap.ExtendedAbstractProcessor;
import com.github.t1.exap.Round;
import com.github.t1.exap.SupportedAnnotationClasses;
import com.github.t1.exap.insight.SourceAlreadyExistsException;
import com.github.t1.exap.insight.Type;
import com.github.t1.jsonbap.api.Bindable;

@SupportedAnnotationClasses({Bindable.class})
public class JsonbAnnotationProcessor extends ExtendedAbstractProcessor {
    private JsonbapConfig context;

    @Override public boolean process(Round round) {
        this.context = new JsonbapConfig(processingEnv);
        note("processor context " + context);
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

    private void generateSerializerFor(Type type) {
        var generator = new JsonbSerializerGenerator(context, type);
        type.note("generate " + generator.className());
        try (var typeGenerator = type.getPackage().openTypeGenerator(generator.className())) {
            generator.generate(typeGenerator);
        } catch (SourceAlreadyExistsException e) {
            type.warning("serializer already exists: " + e.getSourceName());
        }
    }
}
