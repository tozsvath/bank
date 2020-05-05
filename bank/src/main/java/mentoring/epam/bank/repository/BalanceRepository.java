package mentoring.epam.bank.repository;

import mentoring.epam.bank.domain.Balance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BalanceRepository extends MongoRepository<Balance, String> {

    public Balance findByUser(String userName);

}