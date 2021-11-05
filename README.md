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

1. Create some marker interface, e.g. `interface SomeMarker { }`
2. Add `@JsonExclude(SomeMarker.class)` over the field to be excluded

    ```java
    @Data
    class User {
        private String username;
        @JsonExclude(SomeMarker.class)
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
    JsonExcludeFilter filter = new JsonExcludeFilter(SomeMarker.class);
    String result = mapper.writer(new JsonExcludeFilterProvider(filter))
            .writeValueAsString(someObject);
    ```
