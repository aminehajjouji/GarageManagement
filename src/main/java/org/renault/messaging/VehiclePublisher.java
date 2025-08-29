package org.renault.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.renault.config.KafkaConfig;
import org.renault.dto.VehicleDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehiclePublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishVehicleCreated(VehicleDTO vehicleDTO) {
        VehicleEvent event = VehicleEvent.vehicleCreated(vehicleDTO);
        sendVehicleEvent(event);
        log.info("Published vehicle created event: {}", vehicleDTO.getId());
    }

    private void sendVehicleEvent(VehicleEvent event) {
        try {
            kafkaTemplate.send(KafkaConfig.VEHICLE_TOPIC, event.getVehicle().getId().toString(), event);
            log.info("Sent vehicle event: {} for vehicle ID: {}", event.getEventType(), event.getVehicle().getId());
            log.info(event.toString());
        } catch (Exception e) {
            log.error("Error publishing vehicle event: {}", e.getMessage(), e);
        }
    }
}
