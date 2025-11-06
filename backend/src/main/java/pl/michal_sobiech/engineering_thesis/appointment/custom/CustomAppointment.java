package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public interface CustomAppointment {

    public long appointmentId();

    public long enterpriseServiceId();

    public long customerUserId();

    public BigDecimal price();

    public OffsetDateTime startTime();

    public OffsetDateTime endTime();

    public String address();

    public Double longitude();

    public Double latitude();

}