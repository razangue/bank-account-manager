package domain.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import domain.model.Client;

public interface ClientRepository {
    Client save(Client client);

    Optional<Client> retriveClient(String lastName, String firstName, LocalDate birthDate);

    List<Client> findAll();
}
