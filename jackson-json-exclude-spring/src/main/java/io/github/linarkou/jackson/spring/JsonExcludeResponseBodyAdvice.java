package io.github.linarkou.jackson.spring;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import io.github.linarkou.jackson.JsonExclude;
import io.github.linarkou.jackson.ser.JsonExcludeFilter;
import io.github.linarkou.jackson.ser.JsonExcludeFilterProvider;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

@ControllerAdvice
public class JsonExcludeResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.supports(returnType, converterType) && returnType.getMethodAnnotation(JsonExclude.class) != null;
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
                                           MethodParameter returnType, ServerHttpRequest request,
                                           ServerHttpResponse response) {
        JsonExclude annotation = returnType.getMethodAnnotation(JsonExclude.class);
        Assert.isTrue(annotation.value().length != 0,
                "No exclude class in JsonExclude annotation on " + returnType);
        bodyContainer.setFilters(new JsonExcludeFilterProvider(annotation.value()));
    }
}
