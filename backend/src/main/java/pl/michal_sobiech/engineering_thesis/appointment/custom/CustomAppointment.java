package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

public interface CustomAppointment {

    public long appointmentId();

    public long enterpriseServiceId();

    public long customerUserId();

    public Optional<BigDecimal> price();

    public OffsetDateTime startTime();

    public OffsetDateTime endTime();

}