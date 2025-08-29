package org.renault.mapper;

import org.renault.dto.GarageDTO;
import org.renault.model.Garage;

public class GarageMapper {
    public static GarageDTO mapToDTO(Garage garage) {
        GarageDTO dto = new GarageDTO();
        dto.setId(garage.getId());
        dto.setName(garage.getName());
        dto.setAddress(garage.getAddress());
        dto.setTelephone(garage.getTelephone());
        dto.setEmail(garage.getEmail());
        dto.setOpeningHours(garage.getOpeningHours());
        return dto;
    }

    public static Garage mapToEntity(GarageDTO dto) {
        Garage garage = new Garage();
        garage.setName(dto.getName());
        garage.setAddress(dto.getAddress());
        garage.setTelephone(dto.getTelephone());
        garage.setEmail(dto.getEmail());
        garage.setOpeningHours(dto.getOpeningHours());
        return garage;
    }
}