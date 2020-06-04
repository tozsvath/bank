package mentoring.epam.bank.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.utils.SerializationUtils;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse implements Serializable {

    public String id;
    public String user;
    public Double amount;
    public String message;

    public byte[] getBytes() {

        byte[] data = SerializationUtils.serialize(this);

        return data;
    }

}
