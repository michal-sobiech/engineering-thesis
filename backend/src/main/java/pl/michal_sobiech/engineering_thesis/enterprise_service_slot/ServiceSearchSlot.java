package pl.michal_sobiech.engineering_thesis.enterprise_service_slot;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public interface ServiceSearchSlot {

    public Long getEnterpriseServiceId();

    public String getServiceName();

    public String getEnterpriseName();

    public OffsetDateTime getStartTime();

    public OffsetDateTime getEndTime();

    public BigDecimal getPrice();

}