package pl.michal_sobiech.core.time_slot;

import java.time.OffsetDateTime;

public record TimeSlotNoOccupancy(

        OffsetDateTime start,
        OffsetDateTime end

) implements TimeSlot {

}
