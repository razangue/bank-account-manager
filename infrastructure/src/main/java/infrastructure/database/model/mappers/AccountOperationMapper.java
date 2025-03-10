package infrastructure.database.model.mappers;

import org.springframework.stereotype.Component;

import domain.model.AccountOperation;
import infrastructure.database.model.entity.AccountOperationEntity;

@Component
public class AccountOperationMapper {
    public AccountOperationEntity toEntity(AccountOperation operation){
        return AccountOperationEntity.builder()
        .accountBalanceBeforeOperation(operation.getAccountBalanceBeforeOperation())
        .accountBalanceAfterOperation(operation.getAccountBalanceAfterOperation())
        .amount(operation.getAmount())
        .date(operation.getDate())
        .build();
    }

    public AccountOperation toDomainModel(AccountOperationEntity operation){
        return AccountOperation.builder()
        .accountBalanceBeforeOperation(operation.getAccountBalanceBeforeOperation())
        .accountBalanceAfterOperation(operation.getAccountBalanceAfterOperation())
        .amount(operation.getAmount())
        .date(operation.getDate())
        .build();
    }
}
