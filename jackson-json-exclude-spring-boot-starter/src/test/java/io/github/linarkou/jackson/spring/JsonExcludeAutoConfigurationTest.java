package io.github.linarkou.jackson.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class JsonExcludeAutoConfigurationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void getWithPassword() throws Exception {
        mockMvc.perform(get("/with-password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Bob"))
                .andExpect(jsonPath("$.password").value("q12345678"));
    }

    @Test
    void getWithoutPassword() throws Exception {
        mockMvc.perform(get("/without-password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Bob"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }
}