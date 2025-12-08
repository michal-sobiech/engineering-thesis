package pl.michal_sobiech.core.money;

import java.math.BigDecimal;

import org.javamoney.moneta.Money;

public class MoneyUtils {

    public static long getLongMinorUnitsNumber(Money money) {
        return money.getNumber()
                .numberValueExact(BigDecimal.class)
                .multiply(BigDecimal.valueOf(100))
                .longValue();
    }

}
