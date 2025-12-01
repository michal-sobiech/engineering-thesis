package pl.michal_sobiech.engineering_thesis.time_slot;

import java.time.OffsetDateTime;

public record TimeSlotWithOccupancy(

                OffsetDateTime start,
                OffsetDateTime end,
                int freePlaces

) implements TimeSlot {
}