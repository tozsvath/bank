package mentoring.epam.bank.repository.mongodb;

import mentoring.epam.bank.commons.domain.bank.Balance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BalanceRepository extends MongoRepository<Balance, String> {

    public Balance findByUser(String userName);

}