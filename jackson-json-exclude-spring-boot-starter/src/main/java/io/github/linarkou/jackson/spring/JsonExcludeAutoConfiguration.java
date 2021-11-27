package io.github.linarkou.jackson.spring;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({ObjectMapper.class, JsonGenerator.class})
@ComponentScan
public class JsonExcludeAutoConfiguration {
}
