package com.github.t1.jsonbap.test;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.output.EncodingMode;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.DecodingMode;
import com.jsoniter.spi.TypeLiteral;
import com.jsoniter.static_codegen.StaticCodegenConfig;

@SuppressWarnings("unused")
public class JsoniterCodegenConfig implements StaticCodegenConfig {

    @Override
    public void setup() {
        Any.registerEncoders();
        JsonIterator.setMode(DecodingMode.STATIC_MODE);
        JsonStream.setMode(EncodingMode.STATIC_MODE);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public TypeLiteral[] whatToCodegen() {
        return new TypeLiteral[]{
            TypeLiteral.create(Person.class),
            TypeLiteral.create(Address.class)
        };
    }
}
