package infrastructure.database.repositiry;

import java.util.List;

import org.springframework.stereotype.Component;

import domain.interfaces.AccountOperationRepository;
import domain.model.AccountOperation;
import infrastructure.database.model.entity.AccountOperationEntity;
import infrastructure.database.model.mappers.AccountOperationMapper;

@Component
public class AccountOperationRepositoryImpl implements AccountOperationRepository {

    private final AccountOperationJpaRepository accountOperationJpaRepository;
    private final AccountOperationMapper accountOperationMapper;

    public AccountOperationRepositoryImpl(AccountOperationJpaRepository accountOperationJpaRepository,
    AccountOperationMapper accountOperationMapper) {
        this.accountOperationJpaRepository = accountOperationJpaRepository;
        this.accountOperationMapper = accountOperationMapper;
    }

    @Override
    public AccountOperation save(AccountOperation accountOperation) {
        AccountOperationEntity entity = accountOperationMapper.toEntity(accountOperation);
        return accountOperationMapper.toDomainModel(accountOperationJpaRepository.save(entity));
    }

    @Override
    public List<AccountOperation> findAll() {
        return accountOperationJpaRepository.findAll().stream()
                .map(entity -> accountOperationMapper.toDomainModel(entity))
                .toList();
    }

}
