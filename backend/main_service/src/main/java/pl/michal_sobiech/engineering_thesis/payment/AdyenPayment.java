package pl.michal_sobiech.engineering_thesis.payment;

public record AdyenPayment(

        long paymentId,

        String adyenPspReference

) implements Payment {

    public static AdyenPayment fromEntity(PaymentEntity entity) {
        if (entity.getPaymentId() == null || entity.getAdyenPspReference() == null) {
            throw new IllegalArgumentException();
        }

        return new AdyenPayment(
                entity.getPaymentId(),
                entity.getAdyenPspReference());
    }

}
