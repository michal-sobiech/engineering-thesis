package pl.michal_sobiech.core.adyen;

public interface AdyenProperties {

    public String merchantAccount();

    public String apiKey();

    public String hmacKey();

    public String environment();

}
