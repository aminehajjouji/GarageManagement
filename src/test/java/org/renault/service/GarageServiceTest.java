package org.renault.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.renault.dto.GarageDTO;
import org.renault.model.Garage;
import org.renault.model.OpeningTime;
import org.renault.repository.GarageRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GarageServiceTest {

    @Mock
    private GarageRepository garageRepository;

    @InjectMocks
    private GarageService garageService;

    private Garage garage;
    private GarageDTO garageDTO;

    @BeforeEach
    void setUp() {
        Map<DayOfWeek, List<OpeningTime>> openingHours = new HashMap<>();

        List<OpeningTime> mondayHours = new ArrayList<>();
        mondayHours.add(new OpeningTime(LocalTime.of(9, 0), LocalTime.of(11, 0)));
        mondayHours.add(new OpeningTime(LocalTime.of(14, 0), LocalTime.of(18, 0)));

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

        garage = new Garage();
        garage.setId(1L);
        garage.setName("Fes Garage");
        garage.setAddress("123 Ville nouvelle, Fes");
        garage.setTelephone("0612345678");
        garage.setEmail("fes@garage.com");
        garage.setOpeningHours(openingHours);

        garageDTO = new GarageDTO();
        garageDTO.setId(1L);
        garageDTO.setName("Fes Garage");
        garageDTO.setAddress("123  Ville nouvelle, Fes");
        garageDTO.setTelephone("0612345678");
        garageDTO.setEmail("fes@garage.com");
        garageDTO.setOpeningHours(openingHours);
    }

    @Test
    void createGarage_ShouldReturnCreatedGarage() {
        when(garageRepository.save(any(Garage.class))).thenReturn(garage);
        GarageDTO result = garageService.createGarage(garageDTO);

        assertNotNull(result);
        assertEquals(garageDTO.getId(), result.getId());
        assertEquals(garageDTO.getName(), result.getName());
    }

    @Test
    void getGarageById_ShouldReturnGarage_WhenGarageExists() {
        when(garageRepository.findById(1L)).thenReturn(Optional.ofNullable(garage));
        GarageDTO result = garageService.getGarageById(1L);

        assertNotNull(result);
        assertEquals(garageDTO.getId(), result.getId());
        assertEquals(garageDTO.getName(), result.getName());
    }

    @Test
    void getGarageById_ShouldThrowException_WhenGarageDoesNotExist() {
        when(garageRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> garageService.getGarageById(100L));
    }

    @Test
    void updateGarage_ShouldReturnUpdatedGarage_WhenGarageExists() {
        when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));
        when(garageRepository.save(any(Garage.class))).thenReturn(garage);

        garageDTO.setName("Rabat Garage");
        GarageDTO result = garageService.updateGarage(1L, garageDTO);

        assertNotNull(result);
        assertEquals(garageDTO.getName(), result.getName());
    }

    @Test
    void updateGarage_ShouldThrowException_WhenGarageDoesNotExist() {
        when(garageRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> garageService.updateGarage(100L, garageDTO));

    }
}
