package org.renault.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.renault.dto.GarageDTO;
import org.renault.mapper.GarageMapper;
import org.renault.model.Garage;
import org.renault.repository.GarageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class GarageService {

    private final GarageRepository garageRepository;

    public GarageDTO createGarage(GarageDTO garageDTO) {
        Garage garage = GarageMapper.mapToEntity(garageDTO);
        Garage savedGarage = garageRepository.save(garage);
        return GarageMapper.mapToDTO(savedGarage);
    }

    @Transactional(readOnly = true)
    public GarageDTO getGarageById(Long id) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Garage not found with id: " + id));
        return GarageMapper.mapToDTO(garage);
    }

    @Transactional(readOnly = true)
    public Page<GarageDTO> getAllGarages(Pageable pageable) {
        return garageRepository.findAll(pageable)
                .map(GarageMapper::mapToDTO);
    }

    public GarageDTO updateGarage(Long id, GarageDTO garageDTO) {
        Garage existingGarage = garageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Garage not found with id: " + id));

        existingGarage.setName(garageDTO.getName());
        existingGarage.setAddress(garageDTO.getAddress());
        existingGarage.setTelephone(garageDTO.getTelephone());
        existingGarage.setEmail(garageDTO.getEmail());
        existingGarage.setOpeningHours(garageDTO.getOpeningHours());

        Garage updatedGarage = garageRepository.save(existingGarage);
        return GarageMapper.mapToDTO(updatedGarage);
    }

    public void deleteGarage(Long id) {
        if (!garageRepository.existsById(id)) {
            throw new EntityNotFoundException("Garage not found with id: " + id);
        }
        garageRepository.deleteById(id);
    }
}
