package mentoring.epam.bank.commons.repository.rabbitmq;

public enum RabbitmqRouteNames {

    WITHDRAW("WITHDRAW"),
    DEPOSIT("DEPOSIT"),
    ERROR("ERROR")
    ;

    private String queueName;

    RabbitmqRouteNames(String queueName) {
        this.queueName = queueName;
    }
}