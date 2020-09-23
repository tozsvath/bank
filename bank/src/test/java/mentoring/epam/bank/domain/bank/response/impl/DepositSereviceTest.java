package mentoring.epam.bank.domain.bank.response.impl;

import mentoring.epam.bank.domain.bank.BankService;
import mentoring.epam.bank.domain.bank.exceptions.InvalidPaymentTypeException;
import mentoring.epam.bank.domain.bank.paymentImpl.Deposit;
import mentoring.epam.bank.repository.rabbitmq.RabbitmqSenderBank;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.amqp.core.Message;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DepositSereviceTest {

    @InjectMocks
    private DepositSerevice underTest;

    @Mock
    private BankService bankService;

    @Mock (answer = Answers.RETURNS_DEEP_STUBS)
    private RabbitmqSenderBank rabbitmqSenderBank;

    @Mock
    private Message message;

    @Mock
    private Deposit depositMock;

    @Test
    void sendResponseToAtm() throws InvalidPaymentTypeException {

//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setReceivedRoutingKey("deposit");
//        when(message.getMessageProperties()).thenReturn(messageProperties);
//
//        Transaction transaction = Transaction.builder()
//                .user("asd")
//                .amount(10.5)
//                .ccv(1231231)
//                .creditCardNumber(123l)
//                .password("pw")
//                .paymentType(PaymentType.DEPOSIT)
//                .build();
//        byte[] tr = SerializationUtils.serialize(transaction);
//
//        when(message.getBody()).thenReturn(tr);
//        TransactionResponse transactionResponse = TransactionResponse.builder().message("asd")
//                .amount(10.5)
//                .message("")
//                .id("0")
//                .user("user")
//                .build();
//
//        Transaction deserializeTr = (Transaction)SerializationUtils.deserialize(tr);
//
//        Optional<TransactionResponse> trr = Optional.of(transactionResponse);
//
//        when(bankService.executeTransaction(deserializeTr)).thenReturn(trr);
//
//        Optional<TransactionResponse> result = underTest.execute(message);
//        verify(bankService, times(1)).executeTransaction(transaction);
//        Assertions.assertTrue(result.isPresent());
//        Assertions.assertNotNull(result.get().getUser());


//        when(depositMock.executeTransactionForPaymentMethod(transaction)).thenReturn(transactionResponse).thenReturn(transactionResponse);

    }

    @Test
    void getPaymentType() {
    }
}