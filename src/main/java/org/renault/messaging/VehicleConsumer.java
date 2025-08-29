package org.renault.messaging;

import lombok.extern.slf4j.Slf4j;
import org.renault.config.KafkaConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VehicleConsumer {

    @KafkaListener(topics = KafkaConfig.VEHICLE_TOPIC, groupId = "${spring.kafka.consumer.group-id:garage-service}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeVehicleEvent(@Payload VehicleEvent event) {
        log.info("Received vehicle event: {} for vehicle ID: {}", event.getEventType(), event.getVehicle().getId());
        handleVehicleCreated(event);
    }

    private void handleVehicleCreated(VehicleEvent event) {
        log.info("Processing vehicle created event for vehicle: {} - {}", event.getVehicle().getId(), event.getVehicle().getBrand());
        log.info("**************************************************");
        log.info("**************************************************");
        log.info("**********VEHICLE CREATED EVENT**********");
        log.info("**************************************************");
        log.info("**************************************************");

    }

}