package org.renault.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.renault.dto.VehicleDTO;
import org.renault.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @Operation(summary = "Create a vehicle", description = "Creates a new vehicle with the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "vehicle successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<VehicleDTO> createVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        VehicleDTO createdVehicle = vehicleService.createVehicle(vehicleDTO);
        return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a vehicle by ID", description = "Returns vehicle information by its identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "vehicle found"),
            @ApiResponse(responseCode = "404", description = "vehicle not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {
        VehicleDTO vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }

    @Operation(summary = "Get vehicles by garage ID", description = "Returns a list of vehicles associated with a specific garage.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of vehicles successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Garage not found")
    })
    @GetMapping("/garage/{garageId}")
    public ResponseEntity<List<VehicleDTO>> getVehiclesByGarageId(@PathVariable Long garageId) {
        List<VehicleDTO> vehicles = vehicleService.getVehiclesByGarageId(garageId);
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Get vehicles by brand", description = "Returns a list of vehicles matching the specified brand.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of vehicles successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "No vehicles found for the specified brand")
    })
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<VehicleDTO>> getVehiclesByBrand(@PathVariable String brand) {
        List<VehicleDTO> vehicles = vehicleService.getVehiclesByBrand(brand);
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Update a vehicle", description = "Updates an existing vehicle with the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "vehicle successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "404", description = "vehicle not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleDTO vehicleDTO) {
        VehicleDTO updatedVehicle = vehicleService.updateVehicle(id, vehicleDTO);
        return ResponseEntity.ok(updatedVehicle);
    }

    @Operation(summary = "Delete a vehicle by ID", description = "Deletes a vehicle by its identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "vehicle successfully deleted"),
            @ApiResponse(responseCode = "404", description = "vehicle not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

}
