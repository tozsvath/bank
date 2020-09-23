package mentoring.epam.bank.domain.bank.exceptions;

public class InvalidPaymentTypeException extends Exception {

        public InvalidPaymentTypeException(String errorMessage) {
            super(errorMessage);

    }
}
