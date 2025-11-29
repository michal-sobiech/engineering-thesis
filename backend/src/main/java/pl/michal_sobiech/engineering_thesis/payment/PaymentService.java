package pl.michal_sobiech.engineering_thesis.payment;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentService;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AppointmentService appointmentService;

    public void createPaymentAndLinkItToSubject(
            PaymentServiceProvider paymentServiceProvider,
            String pspReference,
            String merchantReference) {
        Payment payment = createPaymentInDatabase(paymentServiceProvider, pspReference);

        var paymentSubjectTypeAndId = MerchantReferenceUtils.extractPaymentSubjectTypeAndId(merchantReference);
        PaymentSubjectType paymentSubjectType = paymentSubjectTypeAndId.getFirst();
        long paymentSubjectId = paymentSubjectTypeAndId.getSecond();

        linkPaymentToSubject(payment.paymentId(), paymentSubjectType, paymentSubjectId);
    }

    private Payment createPaymentInDatabase(
            PaymentServiceProvider paymentServiceProvider,
            String pspReference) {
        PaymentEntity record = switch (paymentServiceProvider) {
            case PaymentServiceProvider.ADYEN -> new PaymentEntity(
                    null,
                    pspReference);
        };
        record = paymentRepository.save(record);
        return Payment.fromEntity(record);
    }

    private void linkPaymentToSubject(
            long paymentId,
            PaymentSubjectType paymentSubjectType,
            long paymentSubjectId) {
        switch (paymentSubjectType) {
            case PaymentSubjectType.APPOINTMENT -> {
                appointmentService.linkPayment(paymentSubjectId, paymentId);
            }
        }
    }
}
