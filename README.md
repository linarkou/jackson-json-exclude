# Jackson Exclude Filter

Jackson doesn't give us easy-to-use tool for excluding fields dynamically, so `@JsonExclude` solves this problem.

Inspired by `@JsonView`, which give an ability to define what fields to **include**.

Usage is also quite similar to `@JsonView`, see below.

## Usage

Add maven dependency:
```xml
<dependency>
   <groupId>io.github.linarkou</groupId>
   <artifactId>jackson-json-exclude</artifactId>
   <version>1.0</version>
</dependency>
```

### Data model

1. Create some marker interface, e.g. `interface ExcludePassword { }`
2. Add `@JsonExclude(ExcludePassword.class)` over the field to be excluded

    ```java
    @Data @AllArgsConstructor @NoArgsConstructor
    class User {
        private String username;
        @JsonExclude(ExcludePassword.class)
        private String password;
    }
    ```

### Serialization

1. Set up Jackson

    ```java
    ObjectMapper mapper = new ObjectMapper();
    mapper.addMixIn(Object.class, JsonExcludeMixIn.class);
    // need to keep working serialization of classes not using @JsonExclude
    mapper.setFilterProvider(new JsonExcludeFilterProvider(new JsonExcludeFilter()));
    ```
2. Create and use filter for serialization

    ```java
    User user = new User("Bob", "q12345678");
    String result = mapper.writer(new JsonExcludeFilterProvider(ExcludePassword.class))
            .writeValueAsString(someObject);
    // result = {"username": "Bob"} (without password)
    ```

### Spring MVC Controller

There is an ability to put `@JsonExclude` annotation on a Spring MVC `@RequestMapping`
 or `@ExceptionHandler` method.

Just add maven dependency:
```xml
<dependency>
   <groupId>io.github.linarkou</groupId>
   <artifactId>jackson-json-exclude-spring-boot-starter</artifactId>
   <version>1.0</version>
</dependency>
```

Then use it like in here:
```java
@JsonExclude(ExcludePassword.class)
@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public User getWithoutPassword() {
    return new User("Bob", "q12345678");
}
// will return {"username": "Bob"} (without password)
```

Full working example can be found [here](./jackson-json-exclude-spring-boot-starter/src/test/java/io/github/linarkou/jackson/spring/TestBootApplication.java)
