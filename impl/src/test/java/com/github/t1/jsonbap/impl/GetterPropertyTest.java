package com.github.t1.jsonbap.impl;

import com.github.t1.exap.insight.Type;
import org.junit.jupiter.api.Test;

import static com.github.t1.exap.reflection.ReflectionProcessingEnvironment.ENV;
import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.IDENTITY;
import static com.github.t1.jsonbap.impl.GetterProperty.getterProperties;
import static org.assertj.core.api.BDDAssertions.then;

class GetterPropertyTest {
    private final Type type = ENV.type(Pojo.class);
    private final JsonbapConfig jsonbapConfig = new JsonbapConfig();
    private final TypeConfig typeConfig = new TypeConfig(IDENTITY);

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

        public boolean isMember() {return true;}

        public boolean issue() {return true;}

        public boolean is() {return true;}

        public String isNot() {return "not";}
    }

    @Test
    void shouldFindRealGetter() {
        var found = getterProperties(jsonbapConfig, typeConfig, type)
                .filter(getter -> "description".equals(getter.name()))
                .findAny();

        then(found).isPresent();
    }

    @Test
    void shouldFindX() {
        var found = getterProperties(jsonbapConfig, typeConfig, type)
                .filter(getter -> "x".equals(getter.name()))
                .findAny();

        then(found).isPresent();
    }

    @Test
    void shouldNotFindStatic() {
        var found = getterProperties(jsonbapConfig, typeConfig, type)
                .filter(getter -> "static".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }

    @Test
    void shouldNotFindPrivate() {
        var found = getterProperties(jsonbapConfig, typeConfig, type)
                .filter(getter -> "private".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }

    @Test
    void shouldNotFindGet() {
        var found = getterProperties(jsonbapConfig, typeConfig, type)
                .filter(getter -> "get".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }

    @Test
    void shouldNotFindGetting() {
        var found = getterProperties(jsonbapConfig, typeConfig, type)
                .filter(getter -> "ting".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }

    @Test
    void shouldNotFindClassGetter() {
        var found = getterProperties(jsonbapConfig, typeConfig, type)
                .filter(getter -> "class".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }

    @Test
    void shouldNotFindVoid() {
        var found = getterProperties(jsonbapConfig, typeConfig, type)
                .filter(getter -> "nothing".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }

    @Test
    void shouldNotFindWithParam() {
        var found = getterProperties(jsonbapConfig, typeConfig, type)
                .filter(getter -> "withParam".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }

    @Test
    void shouldFindMember() {
        var found = getterProperties(jsonbapConfig, typeConfig, type)
                .filter(getter -> "member".equals(getter.name()))
                .findAny();

        then(found).isPresent();
    }

    @Test
    void shouldNotFindIssue() {
        var found = getterProperties(jsonbapConfig, typeConfig, type)
                .filter(getter -> "sue".equals(getter.name()) || "issue".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }

    @Test
    void shouldNotFindIs() {
        var found = getterProperties(jsonbapConfig, typeConfig, type)
                .filter(getter -> "is".equals(getter.name()) || "".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }

    @Test
    void shouldNotFindIsNot() {
        var found = getterProperties(jsonbapConfig, typeConfig, type)
                .filter(getter -> "not".equals(getter.name()))
                .findAny();

        then(found).isEmpty();
    }
}
