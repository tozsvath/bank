package mentoring.epam.bank.commons.domain.bank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum PaymentType {
    WITHDRAW("withdraw"),
    DEPOSIT("deposit");

    private String paymentType;


    PaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @JsonCreator
    public static PaymentType decode(final String code) {
        return Stream.of(PaymentType.values())
                .filter(targetEnum -> targetEnum.getPaymentType()
                        .equalsIgnoreCase(code)).findFirst()
                .orElse(null);
    }

    @JsonValue
    public String getPaymentType() {
        return paymentType;
    }
}