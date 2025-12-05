package pl.michal_sobiech.core.appointment.custom;

import java.util.Optional;

import pl.michal_sobiech.core.appointment.AppointmentEntity;

public class CustomAppointmentFactory {

    public static CustomAppointment fromEntity(AppointmentEntity entity) {
        return Optional.<CustomAppointment>empty()
                .or(() -> UncancelledPendingAppointment.fromEntity(entity))
                .orElseThrow(() -> new IllegalArgumentException());
    }

}