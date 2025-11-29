package pl.michal_sobiech.engineering_thesis.adyen;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.SwaggerCodeGenExample.model.AdyenWebhookRequest;
import org.SwaggerCodeGenExample.model.AdyenWebhookRequestNotificationItem;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.exceptions.exceptions.ForbiddenException;
import pl.michal_sobiech.engineering_thesis.payment.PaymentService;
import pl.michal_sobiech.engineering_thesis.payment.PaymentServiceProvider;

@Service
@RequiredArgsConstructor
public class AdyenWebhookService {

    private final AdyenProperties adyenProperties;
    private final PaymentService paymentService;

    public void handleWebhook(AdyenWebhookRequest request) {
        handleWebhookNotificationItems(request);
    }

    private void handleWebhookNotificationItems(AdyenWebhookRequest request) {
        for (var notificationItem : request.getNotificationItems()) {
            handleWebhookNotificationItem(notificationItem);
        }
    }

    private void handleWebhookNotificationItem(AdyenWebhookRequestNotificationItem notificationItem) {
        verifyNotificationItemSignature(notificationItem);

        String merchantReference = notificationItem.getMerchantReference();
        String pspReference = notificationItem.getPspReference();

        paymentService.createPaymentAndLinkItToSubject(PaymentServiceProvider.ADYEN, pspReference, merchantReference);
    }

    private void verifyNotificationItemSignature(AdyenWebhookRequestNotificationItem notificationItem) {
        if (isNotificationItemSignatureValid(notificationItem)) {
            throw new ForbiddenException();
        }
    }

    private boolean isNotificationItemSignatureValid(AdyenWebhookRequestNotificationItem notificationItem) {
        String expectedHmacBase64 = notificationItem.getAdditionalData().getHmacSignature();
        String actualHmacBase64 = createHmacSignatureBase64(notificationItem);
        return actualHmacBase64.equals(expectedHmacBase64);
    }

    private String createHmacSignatureBase64(AdyenWebhookRequestNotificationItem notificationItem) {
        byte[] hmacSignature = createHmacSignature(notificationItem);
        return Base64.getEncoder().encodeToString(hmacSignature);
    }

    private byte[] createHmacSignature(AdyenWebhookRequestNotificationItem item) {
        // https://docs.adyen.com/development-resources/webhooks/secure-webhooks/verify-hmac-signatures

        String hmacPayload = createHmacPayloadForNotificationItem(item);
        byte[] hmacPayloadUtf8 = hmacPayload.getBytes(StandardCharsets.UTF_8);

        byte[] hmacKeyBytes;
        try {
            hmacKeyBytes = Hex.decodeHex(adyenProperties.hmacKey().toCharArray());
        } catch (DecoderException exception) {
            throw new IllegalArgumentException("String isn't a hex string");
        }

        HmacUtils utils = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, hmacKeyBytes);

        return utils.hmac(hmacPayloadUtf8);
    }

    private String createHmacPayloadForNotificationItem(AdyenWebhookRequestNotificationItem item) {
        String originalReference = "";
        return createHmacPaylod(
                item.getPspReference(),
                originalReference,
                item.getMerchantAccountCode(),
                item.getMerchantReference(),
                item.getAmount().getValue(),
                item.getAmount().getCurrency(),
                item.getEventCode(),
                item.getSuccess());
    }

    private String createHmacPaylod(
            String pspReference,
            String originalReference,
            String merchantAccountCode,
            String merchantReference,
            long value,
            String currency,
            String eventCode,
            boolean success) {
        String delimiter = ":";
        return String.join(delimiter,
                pspReference,
                originalReference,
                merchantAccountCode,
                merchantReference,
                String.valueOf(value),
                currency,
                eventCode,
                String.valueOf(success));
    }

}