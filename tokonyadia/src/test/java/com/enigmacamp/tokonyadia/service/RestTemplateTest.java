package com.enigmacamp.tokonyadia.service;

import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Data
@Builder
class Post implements Serializable {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;

    @Override
    public String toString() {
        return "Post{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}

@Data
@Builder
class Todo implements Serializable {
    private Integer userId;
    private Integer id;
    private String title;
    private Boolean completed;
}

public class RestTemplateTest {
    RestTemplate restTemplate;
    private final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @BeforeEach
    void setupTest() {
        restTemplate = new RestTemplate();

    }

    @Test
    void getMapTest() {
        ResponseEntity<Post> response = restTemplate.getForEntity(BASE_URL + "/posts/1", Post.class);
        HttpHeaders httpHeaders = restTemplate.headForHeaders(BASE_URL + "/posts/1");

        Assertions.assertTrue(Objects.requireNonNull(httpHeaders.getContentType()).includes(MediaType.APPLICATION_JSON));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Post body = response.getBody();
        assert body != null;
        System.out.println(body.getTitle());
    }

    @Test
    void getClassTest() {
        ResponseEntity<List<Todo>> response = restTemplate.exchange(
                BASE_URL + "/todos",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Todo> todos = response.getBody();

        assert todos != null;
        for (Todo todo: todos) {
            System.out.println("Todo: " + todo.getTitle() + ", completed: " + todo.getCompleted());
        }
    }

    @Test
    void postTest() {
        Post post = Post.builder()
                .userId(1)
                .id(1)
                .title("Pegi Setiawan")
                .body("Akhirnya Pegi setiawan dibebaskan dari penjara karena terbukti tidak bersalah")
                .build();

        ResponseEntity<Post> response = restTemplate.postForEntity(BASE_URL + "/posts", post, Post.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        System.out.println(response.getBody());
    }

    @Test
    void getWithAuthTest() {
        String username = "admin";
        String password = "1234";
        String token = System.getenv("GITHUB_TOKEN");

        // Bearer Token
        HttpHeaders headers = new HttpHeaders() {{
            set("Authorization", "Bearer " + token);
        }};

        //Basic Auth
        //HttpHeaders headers = createBasicAuthHeaders(username, password);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.github.com/user",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());
    }

    HttpHeaders createBasicAuthHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String( encodedAuth );
            set("Authorization", authHeader);
            set("Content-Type", "application/json");
        }};
    }
}
