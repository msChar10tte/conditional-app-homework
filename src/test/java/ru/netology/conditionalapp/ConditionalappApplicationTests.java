package ru.netology.conditionalapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConditionalappApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    private static GenericContainer<?> devApp;
    private static GenericContainer<?> prodApp;

    @BeforeAll
    public static void setUp() {
        devApp = new GenericContainer<>(DockerImageName.parse("devapp"))
                .withExposedPorts(8080); // Порт, который слушает приложение внутри контейнера

        prodApp = new GenericContainer<>(DockerImageName.parse("prodapp"))
                .withExposedPorts(8081); // Порт, который слушает приложение внутри контейнера

        devApp.start();
        prodApp.start();
    }

    @AfterAll
    public static void tearDown() {
        devApp.stop();
        prodApp.stop();
    }

    @Test
    void devProfileTest() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(
                "http://localhost:" + devApp.getMappedPort(8080) + "/profile", String.class);
        System.out.println("Dev profile response: " + forEntity.getBody());
        assertEquals("Current profile is dev", forEntity.getBody());
    }

    @Test
    void prodProfileTest() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(
                "http://localhost:" + prodApp.getMappedPort(8081) + "/profile", String.class);
        System.out.println("Prod profile response: " + forEntity.getBody());
        assertEquals("Current profile is production", forEntity.getBody());
    }
}