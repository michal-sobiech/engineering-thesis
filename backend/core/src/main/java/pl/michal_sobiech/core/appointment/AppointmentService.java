package pl.michal_sobiech.core.appointment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.appointment.custom.CustomAppointmentQueryService;
import pl.michal_sobiech.core.appointment.non_custom.NonCustomAppointmentQueryService;
import pl.michal_sobiech.core.currency_iso.CurrencyIso;
import pl.michal_sobiech.core.enterprise_member.EnterpriseMember;
import pl.michal_sobiech.core.enterprise_member.EnterpriseMemberService;
import pl.michal_sobiech.core.payment.PaymentServiceProvider;

@RequiredArgsConstructor
public class AppointmentService {

    private final EnterpriseMemberService enterpriseMemberService;
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

    public void markAppointmentAsPaidOnline(
            long appointmentId,
            PaymentServiceProvider paymentServiceProvider,
            String pspReference) {
        AppointmentEntity record = appointmentRepository.findById(appointmentId).orElseThrow();
        record.setPaid(true);
        record.setPaymentServiceProvider(paymentServiceProvider);
        record.setPspReference(pspReference);
        record.setWasPayoutProcessed(false);
        appointmentRepository.save(record);
    }

    public List<UncancelledScheduledAppointment> getPastScheduledAppointmentsWaitingForPayoutProcessing() {
        return appointmentRepository.findPastScheduledAppointmentsWaitingForPayoutProcessing()
                .stream()
                .map(UncancelledScheduledAppointment::fromEntityOrThrow)
                .collect(Collectors.toList());
    }

    public void setAppointmentPayoutProcessed(long appointmentId) {
        AppointmentEntity record = appointmentRepository.findById(appointmentId).orElseThrow();
        record.setWasPayoutProcessed(true);
        appointmentRepository.save(record);
    }

}
