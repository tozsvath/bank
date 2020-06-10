package mentoring.epam.bank.commons.rabbitmq;

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