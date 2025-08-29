package org.renault.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class OpeningTime {
    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;
}