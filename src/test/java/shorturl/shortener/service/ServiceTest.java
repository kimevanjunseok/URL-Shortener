package shorturl.shortener.service;

import org.springframework.boot.test.context.SpringBootTest;

import shorturl.shortener.config.TestRedisConfiguration;

// @DataJpaTest
@SpringBootTest(classes = TestRedisConfiguration.class)
class ServiceTest {
}
