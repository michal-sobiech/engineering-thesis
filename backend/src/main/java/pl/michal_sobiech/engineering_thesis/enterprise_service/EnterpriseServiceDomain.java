package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.math.BigDecimal;
import java.time.ZoneId;

import org.SwaggerCodeGenExample.model.Location;

import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;

public interface EnterpriseServiceDomain {

    // TODO with "get" or without?

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
