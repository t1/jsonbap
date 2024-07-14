package com.github.t1.jsonbap.impl;

import com.github.t1.exap.ExtendedAbstractProcessor;
import com.github.t1.exap.Round;
import com.github.t1.exap.SupportedAnnotationClasses;
import com.github.t1.exap.reflection.Type;
import com.github.t1.jsonbap.api.Jsonb;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_11;

@Slf4j
@SupportedSourceVersion(RELEASE_11)
@SupportedAnnotationClasses({Jsonb.class})
public class JsonbAnnotationProcessor extends ExtendedAbstractProcessor {
    @Override public boolean process(Round round) {
        round.typesAnnotatedWith(Jsonb.class).forEach(this::process);
        return false;
    }

    private void process(Type type) {
        var generator = new JsonbWriterGenerator(type);
        System.out.println("generate " + generator.className());
        try (var typeGenerator = type.getPackage().openTypeGenerator(generator.className())) {
            generator.generate(typeGenerator);
        }
    }
}
