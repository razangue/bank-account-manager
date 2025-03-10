package application.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.requests.AccountOperationRequest;
import application.requests.AccountOperationSearchRequest;
import domain.model.AccountOperation;
import domain.services.AccountOperationService;
import domain.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/bank-account")
public class AccountOperationController {
    private final AccountOperationService accountOperationService;
    private final AccountService accountService;

    public AccountOperationController(AccountOperationService accountOperationService, AccountService accountService) {
        this.accountOperationService = accountOperationService;
        this.accountService = accountService;
    }

    @PostMapping("/deposit")
    @Operation(summary = "Make a deposit", description = "Make a deposit in my account")
    public ResponseEntity<String> makeDeposit(@RequestBody AccountOperationRequest operationRequest) {
        accountOperationService.makeDeposit(operationRequest.getAccountNumber(), operationRequest.getAmount());
        return ResponseEntity.ok("Deposit have been successfully done");
    }

    @PostMapping("/withdrawal")
    @Operation(summary = "Make a withdrawal", description = "Make a withdrawal in my account")
    public ResponseEntity<String> makeWithdrawal(@RequestBody AccountOperationRequest operationRequest) {
        accountOperationService.makeWithdrawal(operationRequest.getAccountNumber(), operationRequest.getAmount());
        return ResponseEntity.ok("withdrawal have been successfully done");
    }

    @GetMapping("/operation")
    @Operation(summary = "Retrieve an operation", description = "Retrieve an operation in my account")
    public ResponseEntity<AccountOperation> retrieveOperation(@RequestBody AccountOperationSearchRequest request) {
        AccountOperation operation = accountService.findOperation(request.getAccountNumber(), request.getAmount(),
                request.getType());
        return ResponseEntity.ok(operation);
    }

    @GetMapping("/operations")
    @Operation(summary = "Retrieve all operations", description = "Retrieve all operations in my account")
    public ResponseEntity<List<AccountOperation>> retrieveOperations(@RequestBody String accountNumber) {
        return ResponseEntity.ok(accountService.findAllOperations(accountNumber));
    }
}
