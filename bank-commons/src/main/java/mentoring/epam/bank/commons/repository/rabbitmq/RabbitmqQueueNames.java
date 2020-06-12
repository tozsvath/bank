package mentoring.epam.bank.commons.repository.rabbitmq;

public enum RabbitmqQueueNames {

    ATM_TO_BANK("ATM_TO_BANK"),
    BANK_TO_ATM("BANK_TO_ATM"),
    BANK_TO_ATM_ERROR("BANK_TO_ATM_ERROR")
    ;


    private String queueName;

    RabbitmqQueueNames(String queueName) {
        this.queueName = queueName;
    }
}
