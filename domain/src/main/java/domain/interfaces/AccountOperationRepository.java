package domain.interfaces;

import java.util.List;

import domain.model.AccountOperation;

public interface AccountOperationRepository {
    AccountOperation save(AccountOperation accountOperation);

    List<AccountOperation> findAll();
}
