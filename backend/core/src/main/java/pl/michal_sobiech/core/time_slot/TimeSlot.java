package pl.michal_sobiech.core.time_slot;

import java.time.OffsetDateTime;

public interface TimeSlot {

    public OffsetDateTime start();

    public OffsetDateTime end();
}