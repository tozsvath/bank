package mentoring.epam.bank;

import mentoring.epam.bank.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@Configuration
class ApplicationConfig {

    @Autowired
    private BalanceRepository repository;

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {

        repository.deleteAll();
        Resource sourceData = new ClassPathResource("mongo.json");

        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[] { sourceData });
        return factory;
    }
}
