package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.math.BigDecimal;
import java.time.Instant;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;

public interface CustomAppointment {

    public long appointmentId();

    public long enterpriseServiceId();

    public long customerUserId();

    public BigDecimal price();

    public CurrencyIso currency();

    public Instant startInstant();

    public Instant endInstant();

    public Location location();

}