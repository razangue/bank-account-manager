package domain.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import domain.interfaces.ClientRepository;
import domain.model.Client;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client create(Client client) {
        return clientRepository.save(client);
    }

    public Client retriveClient(String lastName, String firstName, LocalDate birthDate) {
        return clientRepository.retriveClient(lastName, firstName, birthDate).orElse(null);
    }
}
