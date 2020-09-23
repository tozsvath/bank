package mentoring.epam.bank.commons.repository.rabbitmq.converter;

import mentoring.epam.bank.commons.domain.bank.Transaction;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

public class TransactionMessageConverter extends AbstractMessageConverter {

    public TransactionMessageConverter() {
        super(new MimeType("application", "transaction"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return (Transaction.class == clazz);
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        Object payload = message.getPayload();

        return (payload instanceof Transaction ? payload : new Transaction());
    }

}
