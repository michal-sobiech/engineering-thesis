package pl.michal_sobiech.core.time_slot;

import java.time.OffsetDateTime;

public record TimeSlotWithOccupancy(

        OffsetDateTime start,
        OffsetDateTime end,
        int freePlaces

) implements TimeSlot {
}