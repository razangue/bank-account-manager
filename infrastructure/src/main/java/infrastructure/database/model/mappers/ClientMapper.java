package infrastructure.database.model.mappers;

import domain.model.Client;
import infrastructure.database.model.entity.ClientEntity;

public class ClientMapper {
    public ClientEntity toEntity(Client client) {
        return ClientEntity.builder()
                .lastName(client.getLastName())
                .firstName(client.getFirstName())
                .birthDate(client.getBirthDate())
                .gender(client.getGender())
                .nationality(client.getNationality())
                .build();
    }

    public Client toDomainModel(ClientEntity client) {
        return Client.builder()
                .lastName(client.getLastName())
                .firstName(client.getFirstName())
                .birthDate(client.getBirthDate())
                .gender(client.getGender())
                .nationality(client.getNationality())
                .build();
    }

}
