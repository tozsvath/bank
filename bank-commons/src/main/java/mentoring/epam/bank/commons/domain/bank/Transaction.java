package mentoring.epam.bank.commons.domain.bank;

import lombok.*;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction implements Serializable {

    private String user;
    private String password;
    private PaymentType paymentType;
    private Double amount;
    private Integer ccv;
    private Long creditCardNumber;


    public byte[] getBytes() {

        byte[] data = SerializationUtils.serialize(this);
        return data;
    }
}
