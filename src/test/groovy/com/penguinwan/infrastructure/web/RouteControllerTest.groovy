package com.penguinwan.infrastructure.web

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class RouteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void "return 200 when GET url is correct"() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/direct").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
    }

    @Test
    void "return 404 when GET url is incorrect"() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/invalid").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().is(404))
    }

}
