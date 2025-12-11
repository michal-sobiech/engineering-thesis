package pl.michal_sobiech.core.enterprise_service;

import java.math.BigDecimal;
import java.time.ZoneId;

import pl.michal_sobiech.core.currency_iso.CurrencyIso;
import pl.michal_sobiech.core.location.Location;

public interface EnterpriseServiceDomain {

    public long enterpriseServiceId();

    public long enterpriseId();

    public String name();

    public String description();

    public Location location();

    public ZoneId timezone();

    public EnterpriseServiceCathegory cathegory();

    public BigDecimal price();

    public CurrencyIso currency();

    public boolean suspendedByAdmin();

}
