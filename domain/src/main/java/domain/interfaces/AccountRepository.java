package domain.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import domain.model.Account;
import domain.model.AccountOperation;
import domain.model.AccountOperationType;

public interface AccountRepository {
     Account save(Account account);

     Optional<Account> findByAccountNumber(String accountNumber);

     List<Account> findAll();

     List<AccountOperation> findAllOperations(String accountNumber);

     Optional<AccountOperation> findOperation(String accountNumber, BigDecimal amount,
               AccountOperationType type);
}
