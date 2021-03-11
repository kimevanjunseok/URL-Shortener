package shorturl.shortener.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@WebMvcTest
class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    public void createByRequestBody(final String path, final String content,
            final ResultMatcher status, final ResultMatcher expect) throws Exception {
        this.mockMvc.perform(post(path)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status)
                .andExpect(expect)
                .andDo(print());
    }

    public void page(final String path, final ResultMatcher status, final ResultMatcher expect) throws Exception {
        this.mockMvc.perform(get(path))
                .andExpect(status)
                .andExpect(expect)
                .andDo(print());
    }

    public void redirectByPathVariable(final String path, final String uriVar, final ResultMatcher header) throws Exception {
        this.mockMvc.perform(get(path, uriVar))
                .andExpect(status().is3xxRedirection())
                .andExpect(header)
                .andDo(print());
    }
}
