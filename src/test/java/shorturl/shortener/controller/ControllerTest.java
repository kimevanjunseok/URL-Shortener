package shorturl.shortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class ControllerTest {

    protected static final String URL = "http://localhost:8080/";

    @Autowired
    protected MockMvc mockMvc;
}
