package com.github.linarkou.jackson;

import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JsonExcludeFilterProvider extends SimpleFilterProvider {
    public JsonExcludeFilterProvider(JsonExcludeFilter jsonExcludeFilter) {
        this.addFilter("jsonExclude", jsonExcludeFilter);
    }
}
