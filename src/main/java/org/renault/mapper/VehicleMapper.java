package org.renault.mapper;

import org.renault.dto.GarageDTO;
import org.renault.dto.VehicleDTO;
import org.renault.model.Garage;
import org.renault.model.Vehicle;

public class VehicleMapper {

    public static VehicleDTO mapToDTO(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setBrand(vehicle.getBrand());
        dto.setManufacturingYear(vehicle.getManufacturingYear());
        dto.setFuelType(vehicle.getFuelType());
        dto.setGarageId(vehicle.getGarage().getId());
        return dto;
    }

    public static Vehicle mapToEntity(VehicleDTO dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(dto.getBrand());
        vehicle.setManufacturingYear(dto.getManufacturingYear());
        vehicle.setFuelType(dto.getFuelType());
        return vehicle;
    }
}