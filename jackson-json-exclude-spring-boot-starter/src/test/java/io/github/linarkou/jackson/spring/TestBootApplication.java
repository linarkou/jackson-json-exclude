package io.github.linarkou.jackson.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.linarkou.jackson.JsonExclude;
import io.github.linarkou.jackson.ser.JsonExcludeFilter;
import io.github.linarkou.jackson.ser.JsonExcludeFilterProvider;
import io.github.linarkou.jackson.ser.JsonExcludeMixIn;
import io.github.linarkou.jackson.spring.testdata.ExcludePassword;
import io.github.linarkou.jackson.spring.testdata.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestBootApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().addMixIn(Object.class, JsonExcludeMixIn.class)
                .setFilterProvider(new JsonExcludeFilterProvider());
    }

    @GetMapping(value = "/with-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return new User("Bob", "q12345678");
    }

    @JsonExclude(ExcludePassword.class)
    @GetMapping(value = "/without-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getWithoutPassword() {
        return new User("Bob", "q12345678");
    }
}
