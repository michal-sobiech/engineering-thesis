package pl.michal_sobiech.engineering_thesis.payment;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.util.Pair;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class MerchantReferenceUtils {

    private static final BiMap<PaymentSubjectType, String> prefixes = HashBiMap.create(Map.of(
            PaymentSubjectType.APPOINTMENT, "APPOINTMENT_"));

    public static Pair<PaymentSubjectType, Long> extractPaymentSubjectTypeAndId(
            String merchantReference) {
        for (var entry : prefixes.entrySet()) {
            PaymentSubjectType merchantReferenceType = entry.getKey();
            String prefix = entry.getValue();

            String patternString = "^" + prefix + "(\\d+)$";
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(merchantReference);

            if (matcher.find()) {
                long paymentSubjectId = Long.parseLong(matcher.group(1));
                return Pair.of(merchantReferenceType, paymentSubjectId);
            }
        }
        throw new IllegalArgumentException();
    }

    public static String createMerchantReference(PaymentSubjectType paymentSubjectType, long paymentSubjectId) {
        String prefix = prefixes.get(paymentSubjectType);
        if (prefix == null) {
            throw new IllegalArgumentException();
        }

        return prefix + String.valueOf(paymentSubjectId);
    }

}
