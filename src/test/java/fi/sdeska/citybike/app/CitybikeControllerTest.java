package fi.sdeska.citybike.app;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import fi.sdeska.citybike.data.Journey;
import fi.sdeska.citybike.data.Station;
import fi.sdeska.citybike.service.JourneyService;
import fi.sdeska.citybike.service.StationService;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
class CitybikeControllerTest {

    @MockBean
    private StationService stationService;
    @MockBean
    private JourneyService journeyService;

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldGetAllStations() throws Exception {

        Station s1 = Station.builder()
                            .fId(15L)
                            .id(527L)
                            .build();
        Station s2 = Station.builder()
                            .fId(1L)
                            .id(1L)
                            .build();

        when(stationService.fetchAllStations()).thenReturn(Lists.newArrayList(s1, s2));
        mvc.perform(get("/stations"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", Matchers.hasSize(2)))
           .andExpect(jsonPath("$[0].id", Matchers.equalTo(527)))
           .andExpect(jsonPath("$[1].id", Matchers.equalTo(1)));

    }

    @Test
    void testGetAllJourneys() throws Exception {

        Journey j1 = Journey.builder()
                            .id(2L)
                            .build();
        Journey j2 = Journey.builder()
                            .id(5L)
                            .build();

        when(journeyService.fetchAllJourneys()).thenReturn(Lists.newArrayList(j1, j2));
        mvc.perform(get("/journeys"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", Matchers.hasSize(2)))
           .andExpect(jsonPath("$[0].id", Matchers.equalTo(2)))
           .andExpect(jsonPath("$[1].id", Matchers.equalTo(5)));

    }

}
