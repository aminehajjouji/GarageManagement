package org.renault.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.renault.dto.VehicleDTO;
import org.renault.mapper.VehicleMapper;
import org.renault.model.Garage;
import org.renault.model.Vehicle;
import org.renault.repository.GarageRepository;
import org.renault.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final GarageRepository garageRepository;

    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        Garage garage = garageRepository.findById(vehicleDTO.getGarageId())
                .orElseThrow(() -> new EntityNotFoundException("Garage not found with id: " + vehicleDTO.getGarageId()));

        if (!garage.canAddVehicle()) {
            throw new IllegalStateException("Garage has reached maximum capacity of 50 vehicles");
        }

        Vehicle vehicle = VehicleMapper.mapToEntity(vehicleDTO);
        vehicle.setGarage(garage);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return VehicleMapper.mapToDTO(savedVehicle);
    }

    @Transactional(readOnly = true)
    public VehicleDTO getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with id: " + id));
        return VehicleMapper.mapToDTO(vehicle);
    }

    @Transactional(readOnly = true)
    public List<VehicleDTO> getVehiclesByGarageId(Long garageId) {
        if (!garageRepository.existsById(garageId)) {
            throw new EntityNotFoundException("Garage not found with id: " + garageId);
        }
        return vehicleRepository.findByGarageId(garageId).stream()
                .map(VehicleMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VehicleDTO> getVehiclesByBrand(String brand) {
        return vehicleRepository.findAllByBrand(brand).stream()
                .map(VehicleMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public VehicleDTO updateVehicle(Long id, VehicleDTO vehicleDTO) {
        Vehicle existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with id: " + id));

        if (!existingVehicle.getGarage().getId().equals(vehicleDTO.getGarageId())) {
            Garage newGarage = garageRepository.findById(vehicleDTO.getGarageId())
                    .orElseThrow(() -> new EntityNotFoundException("Garage not found with id: " + vehicleDTO.getGarageId()));

            if (!newGarage.canAddVehicle()) {
                throw new IllegalStateException("New garage has reached maximum capacity of 50 vehicles");
            }
            existingVehicle.setGarage(newGarage);
        }

        existingVehicle.setBrand(vehicleDTO.getBrand());
        existingVehicle.setManufacturingYear(vehicleDTO.getManufacturingYear());
        existingVehicle.setFuelType(vehicleDTO.getFuelType());

        Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);

        return VehicleMapper.mapToDTO(updatedVehicle);
    }

    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new EntityNotFoundException("Vehicle not found with id: " + id);
        }
        vehicleRepository.deleteById(id);

    }
}
