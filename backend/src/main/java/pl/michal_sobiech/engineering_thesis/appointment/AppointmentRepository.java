package pl.michal_sobiech.engineering_thesis.appointment;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

        @Query("""
                        SELECT appointment
                        FROM AppointmentEntity appointment
                        WHERE appointment.start >= :from
                        AND appointment.end <= :to
                        AND appointment.enterpriseServiceId = :serviceId
                        """)
        public List<AppointmentEntity> findAllInRange(
                        @Param("serviceId") long serviceId,
                        @Param("from") OffsetDateTime from,
                        @Param("to") OffsetDateTime to);

        @Query("""
                        SELECT appointment
                        FROM AppointmentEntity appointment
                        WHERE appointment.start >= :from
                        AND appointment.end <= :to
                        AND appointment.enterpriseServiceId = :serviceId
                        AND appointment.isCustom = true
                        AND appointment.isAccepted = true
                        """)
        public List<AppointmentEntity> findConfirmedInDatetimeRange(
                        @Param("serviceId") long serviceId,
                        @Param("from") OffsetDateTime from,
                        @Param("to") OffsetDateTime to);

}
