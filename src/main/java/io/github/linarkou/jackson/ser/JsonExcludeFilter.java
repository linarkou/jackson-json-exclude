package io.github.linarkou.jackson.ser;

import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import io.github.linarkou.jackson.JsonExclude;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonExcludeFilter extends SimpleBeanPropertyFilter {
    private final Set<Class<?>> classesToExclude;

    public JsonExcludeFilter(Class<?>... classesToExclude) {
        this.classesToExclude = Stream.of(classesToExclude).collect(Collectors.toSet());
    }

    public JsonExcludeFilter(Collection<Class<?>> classesToExclude) {
        this.classesToExclude = new HashSet<>(classesToExclude);
    }

    @Override
    protected boolean include(BeanPropertyWriter writer) {
        return include((PropertyWriter) writer);
    }

    @Override
    protected boolean include(PropertyWriter writer) {
        JsonExclude jsonExclude = writer.getAnnotation(JsonExclude.class);
        if (jsonExclude == null || classesToExclude.isEmpty()) {
            return true;
        }
        for (Class<?> clazz : jsonExclude.value()) {
            if (classesToExclude.contains(clazz)) {
                return false;
            }
        }
        return true;
    }
}
