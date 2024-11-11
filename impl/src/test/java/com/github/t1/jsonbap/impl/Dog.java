package com.github.t1.jsonbap.impl;

import com.github.t1.jsonbap.api.Bindable;

@Bindable
public class Dog implements Pet {
    @SuppressWarnings("unused")
    public boolean getIsDog() {return true;}
}
