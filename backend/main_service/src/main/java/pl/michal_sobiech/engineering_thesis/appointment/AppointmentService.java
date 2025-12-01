package pl.michal_sobiech.engineering_thesis.appointment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.custom.CustomAppointmentQueryService;
import pl.michal_sobiech.engineering_thesis.appointment.non_custom.NonCustomAppointmentQueryService;
import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;
import pl.michal_sobiech.engineering_thesis.enterprise_member.EnterpriseMember;
import pl.michal_sobiech.engineering_thesis.enterprise_member.EnterpriseMemberService;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private EnterpriseMemberService enterpriseMemberService;
    private final NonCustomAppointmentQueryService nonCustomAppointmentQueryService;
    private final CustomAppointmentQueryService customAppointmentQueryService;
    private final AppointmentRepository appointmentRepository;

    public boolean canUserManageAppointment(long userId, long appointmentId) {
        // Appointment with given id must be an appointment of a service of an
        // enterprise where the user with given id is enterprise staff.
        List<Long> enterpriseMemberUserIds = enterpriseMemberService.getEnterpriseMembers(appointmentId)
                .stream()
                .map(EnterpriseMember::getUserId)
                .collect(Collectors.toList());

        return enterpriseMemberUserIds.contains(userId);
    }

    public List<UncancelledScheduledAppointment> getEnterpriseServiceUncancelledFutureScheduledAppointments(
            long customerUserId) {
        List<UncancelledScheduledAppointment> nonCustom = nonCustomAppointmentQueryService
                .getCustomerUncancelledFutureScheduledAppointments(customerUserId);

        List<UncancelledScheduledAppointment> custom = customAppointmentQueryService
                .getCustomerUncancelledFutureScheduledAppointments(customerUserId);

        List<UncancelledScheduledAppointment> all = new ArrayList<>();
        all.addAll(nonCustom);
        all.addAll(custom);

        return all;
    }

    public Pair<BigDecimal, CurrencyIso> getAppointmentPriceAndCurrency(long appointmentId) {
        AppointmentEntity record = appointmentRepository.findById(appointmentId).orElseThrow();
        return Pair.of(record.getPrice(), record.getCurrency());
    }

    public void linkPayment(long appointmentId, long paymentId) {
        AppointmentEntity record = appointmentRepository.findById(appointmentId).orElseThrow();
        record.setPaymentId(paymentId);
        record.setPaid(true);
        appointmentRepository.save(record);
    }

}
