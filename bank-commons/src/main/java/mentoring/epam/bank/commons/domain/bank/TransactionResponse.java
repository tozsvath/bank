package mentoring.epam.bank.commons.domain.bank;

import lombok.*;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse implements Serializable {

    private String id;
    private String user;
    private Double amount;
    private String message;
    private PaymentType paymentType;;

    public byte[] getBytes() {

        byte[] data = SerializationUtils.serialize(this);
        return data;
    }
}
