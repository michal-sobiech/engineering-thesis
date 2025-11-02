package pl.michal_sobiech.engineering_thesis.appointment;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.michal_sobiech.engineering_thesis.utils.DatetimeWindow;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Query("""

            """)
    public List<DatetimeWindow> findTakenDatetimeWindowsOnDate(
            @Param("serviceId") long serviceId,
            @Param("date") OffsetDateTime date);

}
