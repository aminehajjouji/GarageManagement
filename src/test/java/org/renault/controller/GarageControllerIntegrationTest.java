package org.renault.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.renault.dto.GarageDTO;
import org.renault.model.Garage;
import org.renault.model.OpeningTime;
import org.renault.repository.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GarageControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private GarageDTO garageDTO;

    @BeforeEach
    void setUp() {
        garageRepository.deleteAll();

        Map<DayOfWeek, List<OpeningTime>> openingHours = new HashMap<>();

        List<OpeningTime> mondayHours = new ArrayList<>();
        mondayHours.add(new OpeningTime(LocalTime.of(9, 0), LocalTime.of(12, 0)));
        mondayHours.add(new OpeningTime(LocalTime.of(13 ,0), LocalTime.of(18, 0)));

        List<OpeningTime> tuesdayHours = new ArrayList<>();
        tuesdayHours.add(new OpeningTime(LocalTime.of(9, 0), LocalTime.of(18, 0)));

        List<OpeningTime> wednesdayHours = new ArrayList<>();
        wednesdayHours.add(new OpeningTime(LocalTime.of(9, 0), LocalTime.of(18, 0)));

        List<OpeningTime> thursdayHours = new ArrayList<>();
        thursdayHours.add(new OpeningTime(LocalTime.of(9, 0), LocalTime.of(18, 0)));

        List<OpeningTime> fridayHours = new ArrayList<>();
        fridayHours.add(new OpeningTime(LocalTime.of(9, 0), LocalTime.of(18, 0)));

        openingHours.put(DayOfWeek.MONDAY, mondayHours);
        openingHours.put(DayOfWeek.TUESDAY, tuesdayHours);
        openingHours.put(DayOfWeek.WEDNESDAY, wednesdayHours);
        openingHours.put(DayOfWeek.THURSDAY, thursdayHours);
        openingHours.put(DayOfWeek.FRIDAY, fridayHours);

        garageDTO = new GarageDTO();
        garageDTO.setName("Rabat Garage");
        garageDTO.setAddress("123 mahaj ryad, Rabat");
        garageDTO.setTelephone("0612345678");
        garageDTO.setEmail("rabat@garage.com");
        garageDTO.setOpeningHours(openingHours);
    }

    @Test
    void createGarage_ShouldReturnCreatedGarage() throws Exception {
        ResultActions response = mockMvc.perform(post("/api/garages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(garageDTO)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(garageDTO.getName())))
                .andExpect(jsonPath("$.address", is(garageDTO.getAddress())))
                .andExpect(jsonPath("$.telephone", is(garageDTO.getTelephone())))
                .andExpect(jsonPath("$.email", is(garageDTO.getEmail())));
    }
    @Test
    void createGarage_ShouldReturn400_WhenInvalidInput() throws Exception {
        garageDTO.setEmail("invalid-email");
        ResultActions response = mockMvc.perform(post("/api/garages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(garageDTO)));
        response.andDo(print()).andExpect(status().isBadRequest());
    }
    @Test
    void getGarageById_ShouldReturnGarage_WhenGarageExists() throws Exception {
        Garage savedGarage = new Garage();
        savedGarage.setName(garageDTO.getName());
        savedGarage.setAddress(garageDTO.getAddress());
        savedGarage.setTelephone(garageDTO.getTelephone());
        savedGarage.setEmail(garageDTO.getEmail());
        savedGarage.setOpeningHours(garageDTO.getOpeningHours());

        Garage persistedGarage = garageRepository.save(savedGarage);

        ResultActions response = mockMvc.perform(get("/api/garages/{id}", persistedGarage.getId()));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(persistedGarage.getId().intValue())))
                .andExpect(jsonPath("$.name", is(persistedGarage.getName())))
                .andExpect(jsonPath("$.address", is(persistedGarage.getAddress())))
                .andExpect(jsonPath("$.telephone", is(persistedGarage.getTelephone())))
                .andExpect(jsonPath("$.email", is(persistedGarage.getEmail())));
    }

    @Test
    void getGarageById_ShouldReturn404_WhenGarageDoesNotExist() throws Exception {
        ResultActions response = mockMvc.perform(get("/api/garages/{id}", 100L));
        response.andDo(print()).andExpect(status().isNotFound());
    }

}
