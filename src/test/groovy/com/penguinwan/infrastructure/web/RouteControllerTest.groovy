package com.penguinwan.infrastructure.web

import com.penguinwan.domain.IRouteFinder
import com.penguinwan.domain.Station
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import static com.penguinwan.infrastructure.web.RouteController.*
import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class RouteControllerTest {

    @Mock
    IRouteFinder routeFinder

    @InjectMocks
    RouteController routeController

    MockMvc mockMvc

    @Before
    void setup() {
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(routeController).build()
    }

    @Test
    void "return true when route is connected"() throws Exception {
        when(routeFinder.isConnected(Station.of(1), Station.of(2))).thenReturn(true)

        mockMvc.perform(MockMvcRequestBuilders.
                get("/api/direct").
                contentType(MediaType.APPLICATION_JSON).
                param(PARAM_DEPARTURE_STATION_ID, '1').
                param(PARAM_ARRIVAL_STATION_ID, '2')).
                andExpect(status().is(200)).
                andExpect(jsonPath(PARAM_DIRECT_BUS_ROUTE).value('true'))
    }

    @Test
    void "return false when route is not connected"() throws Exception {
        when(routeFinder.isConnected(Station.of(1), Station.of(2))).thenReturn(false)

        mockMvc.perform(MockMvcRequestBuilders.
                get("/api/direct").
                contentType(MediaType.APPLICATION_JSON).
                param(PARAM_DEPARTURE_STATION_ID, '1').
                param(PARAM_ARRIVAL_STATION_ID, '2')).
                andExpect(status().is(200)).
                andExpect(jsonPath(PARAM_DIRECT_BUS_ROUTE).value('false'))
    }

    @Test
    void "return departure and arrival station id"() throws Exception {
        when(routeFinder.isConnected(Station.of(99), Station.of(88))).thenReturn(false)

        mockMvc.perform(MockMvcRequestBuilders.
                get("/api/direct").
                contentType(MediaType.APPLICATION_JSON).
                param(PARAM_DEPARTURE_STATION_ID, '99').
                param(PARAM_ARRIVAL_STATION_ID, '88')).
                andExpect(status().is(200)).
                andExpect(jsonPath(PARAM_DEPARTURE_STATION_ID).value('99')).
                andExpect(jsonPath(PARAM_ARRIVAL_STATION_ID).value('88'))
    }

    @Test
    void "return 400 when departure id is not found"() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                get('/api/direct').
                contentType(MediaType.APPLICATION_JSON).
                param(PARAM_ARRIVAL_STATION_ID, '2')).
                andExpect(status().is(400))
    }

    @Test
    void "return 400 when arrival id is not found"() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                get('/api/direct').
                contentType(MediaType.APPLICATION_JSON).
                param(PARAM_DEPARTURE_STATION_ID, '2')).
                andExpect(status().is(400))
    }

    @Test
    void "return 400 when departure id is not integer"() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                get('/api/direct').
                contentType(MediaType.APPLICATION_JSON).
                param(PARAM_DEPARTURE_STATION_ID, 'a').
                param(PARAM_ARRIVAL_STATION_ID, '2')).
                andExpect(status().is(400))
    }

    @Test
    void "return 400 when arrival id is not integer"() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                get('/api/direct').
                contentType(MediaType.APPLICATION_JSON).
                param(PARAM_DEPARTURE_STATION_ID, '1').
                param(PARAM_ARRIVAL_STATION_ID, '&')).
                andExpect(status().is(400))
    }

    @Test
    void "return 404 when GET url is incorrect"() throws Exception {
        when(routeFinder.isConnected(any(), any())).thenReturn(false)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/invalid").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().is(404))
    }

}
