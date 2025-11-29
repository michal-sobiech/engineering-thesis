package pl.michal_sobiech.engineering_thesis.payment;

public interface Payment {

    public long paymentId();

    public static Payment fromEntity(PaymentEntity entity) {
        if (entity.getAdyenPspReference() != null) {
            return AdyenPayment.fromEntity(entity);
        }

        throw new IllegalArgumentException();
    }

}
