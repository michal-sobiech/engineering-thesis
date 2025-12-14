package pl.michal_sobiech.core.appointment;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.javamoney.moneta.Money;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.appointment.custom.CustomAppointmentQueryService;
import pl.michal_sobiech.core.appointment.non_custom.NonCustomAppointmentQueryService;
import pl.michal_sobiech.core.payment.RefundService;

@RequiredArgsConstructor
public class ScheduledAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final CustomAppointmentQueryService customAppointmentQueryService;
    private final NonCustomAppointmentQueryService nonCustomAppointmentQueryService;
    private final RefundService refundService;

    public List<UncancelledScheduledAppointment> getCustomerUncancelledFutureScheduledAppointments(
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

    public List<UncancelledScheduledAppointment> getPastScheduledAppointmentsOfCustomer(long customerUserId) {
        List<UncancelledScheduledAppointment> nonCustom = nonCustomAppointmentQueryService
                .getCustomerUncancelledPastScheduledAppointments(customerUserId);

        List<UncancelledScheduledAppointment> custom = customAppointmentQueryService
                .getCustomerUncancelledPastScheduledAppointments(customerUserId);

        List<UncancelledScheduledAppointment> all = new ArrayList<>();
        all.addAll(nonCustom);
        all.addAll(custom);

        return all;
    }

    public void markAppointmentAsCancelled(long appointmentId) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        appointment.setCancelled(true);
        appointmentRepository.save(appointment);
    }

    public void markAppointmentAsPaidOnSite(long appointmentId) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        appointment.setPaid(true);
        appointmentRepository.save(appointment);
    }

    private boolean isAppointmentOver(AppointmentEntity appointment) {
        return appointment.getEndTime().isBefore(OffsetDateTime.now());
    }

    public void cancelAppointment(long appointmentId) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        boolean isAppointmentOver = isAppointmentOver(appointment);
        if (!isAppointmentOver && appointment.isPaid()) {
            String pspReference = appointment.getPspReference();
            Money amount = Money.of(
                    appointment.getPrice(),
                    appointment.getCurrency().toString());
            refundService.processRefund(appointment.getPaymentServiceProvider(), pspReference, amount);
        }
        markAppointmentAsCancelled(appointmentId);
    }

}
