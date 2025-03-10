package application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.requests.ClientRequest;
import application.requests.ClientSearchRequest;
import domain.model.Client;
import domain.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    public ClientController (ClientService clientService){
        this.clientService = clientService;
    }
    @PostMapping("/create")
    @Operation(summary = "Create client", description = "Create a client")
    public ResponseEntity<Client> createClient(@RequestBody ClientRequest clientRequest) {
        return ResponseEntity.ok(clientService.create(clientRequest.toClient()));
    }

    @GetMapping("/search")
    @Operation(summary = "Search client", description = "search a client")
    public ResponseEntity<Client> retriveClient(@RequestBody ClientSearchRequest clientSearchRequest) {
        return ResponseEntity.ok(clientService.retriveClient(clientSearchRequest.lastName(), clientSearchRequest.firstName(), clientSearchRequest.birthDate()));
    }
}
