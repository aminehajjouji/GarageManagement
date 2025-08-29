package org.renault.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.renault.dto.VehicleDTO;
import org.renault.enumeration.EventType;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleEvent {

    private EventType eventType;
    private VehicleDTO vehicle;
    private LocalDateTime timestamp;

    public static VehicleEvent vehicleCreated(VehicleDTO vehicle) {
        return new VehicleEvent(EventType.CREATED, vehicle, LocalDateTime.now());
    }
}
