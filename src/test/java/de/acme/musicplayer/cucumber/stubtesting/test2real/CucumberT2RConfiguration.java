package de.acme.musicplayer.cucumber.stubtesting.test2real;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@CucumberContextConfiguration
@SpringBootTest(classes = T2RConfiguration.class)
@DirtiesContext
public class CucumberT2RConfiguration {

//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
//            "postgres:16-alpine"
//    );
//    @LocalServerPort
//    private Integer port;

//    @BeforeAll
//    static void beforeAll() {
//        postgres.start();
//    }
//
//    @AfterAll
//    static void afterAll() {
//        postgres.stop();
//    }
//
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//    }
}
