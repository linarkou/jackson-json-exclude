package io.github.linarkou.jackson.ser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.linarkou.jackson.JsonExclude;
import io.github.linarkou.jackson.ser.JsonExcludeFilter;
import io.github.linarkou.jackson.ser.JsonExcludeFilterProvider;
import io.github.linarkou.jackson.ser.JsonExcludeMixIn;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.matchers.JsonPathMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonExcludeFilterTest {

    @Test
    void testJsonExclude() throws JsonProcessingException {
        User user = new User("admin", "123456", new Role("admin"));

        ObjectMapper objectMapper = new ObjectMapper().addMixIn(Object.class, JsonExcludeMixIn.class);

        JsonExcludeFilter jsonExcludeFilter = new JsonExcludeFilter(ExclusionFlag.class);
        String result = objectMapper.writer(new JsonExcludeFilterProvider(jsonExcludeFilter)).writeValueAsString(user);

        assertEquals("admin", JsonPath.read(result, "$.username"));
        assertThat(result, JsonPathMatchers.hasNoJsonPath("$.password"));
        assertThat(result, JsonPathMatchers.hasNoJsonPath("$.role.roleId"));

        // test on another marker interface
        jsonExcludeFilter = new JsonExcludeFilter(NonExclusionFlag.class);
        result = objectMapper.writer(new JsonExcludeFilterProvider(jsonExcludeFilter)).writeValueAsString(user);

        assertEquals("admin", JsonPath.read(result, "$.username"));
        assertEquals("123456", JsonPath.read(result, "$.password"));
        assertEquals("admin", JsonPath.read(result, "$.role.roleId"));
    }

    interface ExclusionFlag {
    }

    interface NonExclusionFlag {
    }

    static class User {
        private String username;
        @JsonExclude(ExclusionFlag.class)
        private String password;
        private Role role;

        public User(String username, String password, Role role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }
    }

    static class Role {
        @JsonExclude(ExclusionFlag.class)
        private String roleId;

        public Role(String roleId) {
            this.roleId = roleId;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }
    }
}
