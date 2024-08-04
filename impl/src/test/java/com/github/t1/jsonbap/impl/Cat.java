package com.github.t1.jsonbap.impl;

import com.github.t1.jsonbap.api.Bindable;

@Bindable
public class Cat implements JsonbSerializerGeneratorTest.Pet {
    @SuppressWarnings("unused")
    public boolean getIsCat() {return true;}
}
