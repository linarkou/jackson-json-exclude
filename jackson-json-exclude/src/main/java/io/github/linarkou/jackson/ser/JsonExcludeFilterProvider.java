package io.github.linarkou.jackson.ser;

import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JsonExcludeFilterProvider extends SimpleFilterProvider {
    public JsonExcludeFilterProvider(JsonExcludeFilter jsonExcludeFilter) {
        this.addFilter("jsonExclude", jsonExcludeFilter);
    }

    public JsonExcludeFilterProvider(Class<?>... classesToExclude) {
        this.addFilter("jsonExclude", new JsonExcludeFilter(classesToExclude));
    }
}
