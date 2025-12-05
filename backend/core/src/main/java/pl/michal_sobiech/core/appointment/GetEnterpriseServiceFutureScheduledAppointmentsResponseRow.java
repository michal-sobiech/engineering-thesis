package pl.michal_sobiech.core.appointment;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import pl.michal_sobiech.core.currency_iso.CurrencyIso;

public interface GetEnterpriseServiceFutureScheduledAppointmentsResponseRow {

    public Long getAppointmentId();

    public String getUsername();

    public String getUserFirstName();

    public String getUserLastName();

    public String getAddress();

    public OffsetDateTime getStartGlobalDatetime();

    public OffsetDateTime getEndGlobalDatetime();

    public ZoneId getTimezone();

    public BigDecimal getPrice();

    public CurrencyIso getCurrency();

}