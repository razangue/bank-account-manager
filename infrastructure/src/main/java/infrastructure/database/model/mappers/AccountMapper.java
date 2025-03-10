package infrastructure.database.model.mappers;

import org.springframework.stereotype.Component;

import domain.model.Account;
import infrastructure.database.model.entity.AccountEntity;

@Component
public class AccountMapper {
    private final AccountOperationMapper accountOperationMapper;
    private final ClientMapper clientMapper;

    public AccountMapper(AccountOperationMapper accountOperationMapper, ClientMapper clientMapper) {
        this.accountOperationMapper = accountOperationMapper;
        this.clientMapper = clientMapper;
    }

    public AccountEntity toEntity(Account account) {
        return AccountEntity.builder()
                .accountNumber(account.getAccountNumber())
                .currentBalance(account.getCurrentBalance())
                .owners(account.getOwners().stream()
                        .map(owner -> clientMapper.toEntity(owner)).toList())
                .operations(account.getOperations().stream()
                        .map(op -> accountOperationMapper.toEntity(op)).toList())
                .build();
    }

    public Account toDomainModel(AccountEntity account) {
        return Account.builder()
                .accountNumber(account.getAccountNumber())
                .currentBalance(account.getCurrentBalance())
                .owners(account.getOwners().stream()
                        .map(owner -> clientMapper.toDomainModel(owner)).toList())
                .operations(account.getOperations().stream()
                        .map(op -> accountOperationMapper.toDomainModel(op)).toList())
                .build();
    }
}
