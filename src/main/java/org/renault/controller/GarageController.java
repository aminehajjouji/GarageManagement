package org.renault.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.renault.dto.GarageDTO;
import org.renault.service.GarageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/garages")
@RequiredArgsConstructor
public class GarageController {

    private final GarageService garageService;

    @Operation(summary = "Create a garage", description = "Creates a new garage with the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Garage successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<GarageDTO> createGarage(@Valid @RequestBody GarageDTO garageDTO) {
        GarageDTO createdGarage = garageService.createGarage(garageDTO);
        return new ResponseEntity<>(createdGarage, HttpStatus.CREATED);
    }
    @Operation(summary = "Get a garage by ID", description = "Returns garage information by its identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Garage found"),
            @ApiResponse(responseCode = "404", description = "Garage not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GarageDTO> getGarageById(@PathVariable Long id) {
        GarageDTO garage = garageService.getGarageById(id);
        return ResponseEntity.ok(garage);
    }

    @Operation(summary = "Retrieve all garages", description = "Returns a page of garages with pagination and sorting.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of garages successfully retrieved")
    })
    @GetMapping
    public ResponseEntity<Page<GarageDTO>> getAllGarages(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        Page<GarageDTO> garages = garageService.getAllGarages(pageable);
        return ResponseEntity.ok(garages);
    }

    @Operation(summary = "Update a garage", description = "Updates an existing garage with the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Garage successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "404", description = "Garage not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<GarageDTO> updateGarage(
            @PathVariable Long id,
            @Valid @RequestBody GarageDTO garageDTO) {
        GarageDTO updatedGarage = garageService.updateGarage(id, garageDTO);
        return ResponseEntity.ok(updatedGarage);
    }

    @Operation(summary = "Delete a garage by ID", description = "Deletes a garage by its identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Garage successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Garage not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGarage(@PathVariable Long id) {
        garageService.deleteGarage(id);
        return ResponseEntity.noContent().build();
    }

}
