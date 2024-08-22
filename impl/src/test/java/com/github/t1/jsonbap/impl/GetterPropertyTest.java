package com.github.t1.jsonbap.impl;

import com.github.t1.exap.insight.Type;
import org.junit.jupiter.api.Test;

import static com.github.t1.exap.reflection.ReflectionProcessingEnvironment.ENV;
import static com.github.t1.jsonbap.impl.GetterProperties.getterProperties;
import static org.assertj.core.api.BDDAssertions.then;

class GetterPropertyTest {
    private final Type type = ENV.type(Pojo.class);

    @SuppressWarnings("unused")
    static class Pojo {
        public static String getStatic() {return "static";}

        public String getDescription() {return "description";}

        public String getX() {return "x";}

        private String getPrivate() {return "private";}

        public String get() {return "get";}

        public String getting() {return "description";}

        public void getNothing() {}

        public String getWithParam(String param) {return "withParam";}
    }

    @Test
    void shouldFindRealGetter() {
        var found = getterProperties(type)
                .filter(getter -> "description".equals(getter.name()))
                .findAny();

        then(found).isPresent();
    }

    @Test
    void shouldFindX() {
        var found = getterProperties(type)
                .filter(getter -> "x".equals(getter.name()))
                .findAny();

        then(found).isPresent();
    }

    @Test
    void shouldNotFindStatic() {
        var found = getterProperties(type)
                .filter(getter -> "static".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }

    @Test
    void shouldNotFindPrivate() {
        var found = getterProperties(type)
                .filter(getter -> "private".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }

    @Test
    void shouldNotFindGet() {
        var found = getterProperties(type)
                .filter(getter -> "get".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }

    @Test
    void shouldNotFindGetting() {
        var found = getterProperties(type)
                .filter(getter -> "ting".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }

    @Test
    void shouldNotFindClassGetter() {
        var found = getterProperties(type)
                .filter(getter -> "class".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }

    @Test
    void shouldNotFindVoid() {
        var found = getterProperties(type)
                .filter(getter -> "nothing".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }

    @Test
    void shouldNotFindWithParam() {
        var found = getterProperties(type)
                .filter(getter -> "withParam".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }
}
