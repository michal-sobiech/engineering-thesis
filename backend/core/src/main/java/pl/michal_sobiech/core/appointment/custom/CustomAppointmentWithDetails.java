package pl.michal_sobiech.core.appointment.custom;

import java.math.BigDecimal;
import java.time.Instant;

import pl.michal_sobiech.core.currency_iso.CurrencyIso;
import pl.michal_sobiech.core.location.Location;

public interface CustomAppointmentWithDetails {

    public long appointmentId();

    public long enterpriseServiceId();

    public long customerUserId();

    public String getCustomerUsername();

    public String getCustomerFirstName();

    public String getCustomerLastName();

    public BigDecimal price();

    public CurrencyIso currency();

    public Instant startInstant();

    public Instant endInstant();

    public Location location();

}
