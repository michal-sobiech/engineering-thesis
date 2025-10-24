package pl.michal_sobiech.engineering_thesis.time_slot;

import java.time.OffsetDateTime;

public record TimeSlotNoOccupancy(

                OffsetDateTime start,
                OffsetDateTime end

) implements TimeSlot {

}
