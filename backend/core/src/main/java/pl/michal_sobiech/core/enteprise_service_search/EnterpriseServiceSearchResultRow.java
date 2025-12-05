package pl.michal_sobiech.core.enteprise_service_search;

import java.math.BigDecimal;
import java.time.ZoneId;

public interface EnterpriseServiceSearchResultRow {

    public Long getEnterpriseServiceId();

    public String getServiceName();

    public String getEnterpriseName();

    public String getAddress();

    public BigDecimal getPrice();

    public ZoneId getTimezone();

}
