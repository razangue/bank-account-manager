package domain.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import domain.exception.EntityNotFoundException;
import domain.interfaces.AccountRepository;
import domain.model.Account;
import domain.model.AccountBalanceMessage;
import domain.model.AccountOperation;
import domain.model.AccountOperationType;
import jakarta.transaction.Transactional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {

        this.accountRepository = accountRepository;
    }

    @Transactional
    public void updateAccountBalance(AccountBalanceMessage accountBalanceMessage) {
        String accountNumber = accountBalanceMessage.getAccountNumber();
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException(Account.class, accountNumber));

        List<AccountOperation> operations = account.getOperations();
        if (operations == null) {
            operations = new ArrayList<>();
        }
        operations.add(accountBalanceMessage.getOperation());
        account.setOperations(operations);
        account.setCurrentBalance(accountBalanceMessage.getBalance());
        accountRepository.save(account);
    }

    public List<AccountOperation> findAllOperations(String accountNumber) {
        return accountRepository.findAllOperations(accountNumber);
    }

    public AccountOperation findOperation(String accountNumber, BigDecimal amount,
            AccountOperationType type) {
        return accountRepository.findOperation(accountNumber, amount, type).orElse(null);
    }

    //TODO
    public Account createAccount(){
        return null;
    }
}
