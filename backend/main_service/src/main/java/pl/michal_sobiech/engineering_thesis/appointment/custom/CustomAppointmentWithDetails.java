package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.math.BigDecimal;
import java.time.Instant;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;

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
