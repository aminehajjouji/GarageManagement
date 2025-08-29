package org.renault.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleDTO {
    private Long id;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotNull(message = "Manufacturing year is required")
    private Integer manufacturingYear;

    @NotBlank(message = "Fuel type is required")
    private String fuelType;

    private Long garageId;
}
