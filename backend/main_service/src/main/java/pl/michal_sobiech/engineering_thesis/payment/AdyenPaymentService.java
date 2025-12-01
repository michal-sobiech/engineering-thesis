package pl.michal_sobiech.engineering_thesis.payment;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdyenPaymentService {

    private final PaymentRepository paymentRepository;

    public AdyenPayment createAdyenPayment(String adyenPspReference) {
        PaymentEntity record = new PaymentEntity(
                null,
                adyenPspReference);
        record = paymentRepository.save(record);
        return AdyenPayment.fromEntity(record);
    }

}
