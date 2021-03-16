package shorturl.shortener.service;

import org.springframework.boot.test.context.SpringBootTest;

import shorturl.shortener.config.TestRedisConfiguration;

@SpringBootTest(classes = TestRedisConfiguration.class)
class ServiceTest {
}
