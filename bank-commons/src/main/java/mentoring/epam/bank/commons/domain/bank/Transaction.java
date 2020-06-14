package mentoring.epam.bank.commons.domain.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class Transaction implements Serializable {

    private String user;
    private String password;
    private Double amount;
    private Integer ccv;
    private Long creditCardNumber;

    public byte[] getBytes() {

        byte[] data = SerializationUtils.serialize(this);
        return data;
    }
}
