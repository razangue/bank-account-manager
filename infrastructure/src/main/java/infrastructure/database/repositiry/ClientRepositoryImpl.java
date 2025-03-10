package infrastructure.database.repositiry;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import domain.interfaces.ClientRepository;
import domain.model.Client;
import infrastructure.database.model.entity.ClientEntity;
import infrastructure.database.model.mappers.ClientMapper;

public class ClientRepositoryImpl implements ClientRepository {
    private final ClientMapper clientMapper;
    private final ClientJpaRepository clientJpaRepository;

    public ClientRepositoryImpl(ClientMapper clientMapper, ClientJpaRepository clientJpaRepository) {
        this.clientMapper = clientMapper;
        this.clientJpaRepository = clientJpaRepository;
    }

    @Override
    public Client save(Client client) {
        ClientEntity entity = clientMapper.toEntity(client);
        return clientMapper.toDomainModel(clientJpaRepository.save(entity));
    }

    @Override
    public Optional<Client> retriveClient(String lastName, String firstName, LocalDate birthDate) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("lastName", match -> match.equals(lastName))
                .withMatcher("firstName", match -> match.equals(firstName))
                .withMatcher("birthDate", match -> match.equals(birthDate));
        ClientEntity entityExample = ClientEntity.builder()
                .lastName(lastName)
                .firstName(firstName)
                .birthDate(birthDate)
                .build();
        Example<ClientEntity> example = Example.of(entityExample, matcher);
        return clientJpaRepository.findOne(example)
                .map(entity -> clientMapper.toDomainModel(entity));
    }

    @Override
    public List<Client> findAll() {
        return clientJpaRepository.findAll()
                .stream().map(c -> clientMapper.toDomainModel(c))
                .toList();
    }

}
