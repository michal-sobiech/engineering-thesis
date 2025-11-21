package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.util.Optional;

import pl.michal_sobiech.engineering_thesis.appointment.AppointmentEntity;

public class CustomAppointmentFactory {

    public static CustomAppointment fromEntity(AppointmentEntity entity) {
        return Optional.<CustomAppointment>empty()
                .or(() -> UncancelledPendingAppointment.fromEntity(entity))
                .orElseThrow(() -> new IllegalArgumentException());
    }

}