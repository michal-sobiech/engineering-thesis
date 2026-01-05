package pl.michal_sobiech.core.enterprise_service_slot_template;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.michal_sobiech.core.converter.DayOfWeekConverter;

@Entity
@Table(name = "enterprise_service_slot_template")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseServiceSlotTemplateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enterprise_service_slot_template_id")
    private long enterpriseServiceSlotTemplateId;

    @Column(name = "enterprise_service_id")
    private long enterpriseServiceId;

    @Convert(converter = DayOfWeekConverter.class)
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Nullable
    @Column(name = "max_occupancy", nullable = true)
    private Short maxOccupancy;

}
