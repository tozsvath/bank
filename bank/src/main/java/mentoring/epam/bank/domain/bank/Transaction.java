package mentoring.epam.bank.domain.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.data.annotation.Id;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
