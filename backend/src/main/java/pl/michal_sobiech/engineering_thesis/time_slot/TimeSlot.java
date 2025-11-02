package pl.michal_sobiech.engineering_thesis.time_slot;

import java.time.OffsetDateTime;

public interface TimeSlot {

    public OffsetDateTime start();

    public OffsetDateTime end();
}