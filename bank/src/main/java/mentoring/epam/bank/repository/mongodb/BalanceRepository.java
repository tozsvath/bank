package mentoring.epam.bank.repository.mongodb;

import mentoring.epam.bank.commons.domain.bank.Balance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface BalanceRepository extends MongoRepository<Balance, String> {

    public Optional<Balance> findByUser(String userName);

    public List<Balance> findAll();

}