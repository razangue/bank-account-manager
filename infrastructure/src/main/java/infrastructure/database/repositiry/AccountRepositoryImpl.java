package infrastructure.database.repositiry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import domain.interfaces.AccountRepository;
import domain.model.Account;
import domain.model.AccountOperation;
import domain.model.AccountOperationType;
import infrastructure.database.model.entity.AccountEntity;
import infrastructure.database.model.mappers.AccountMapper;

@Component
public class AccountRepositoryImpl implements AccountRepository {
    private final AccountMapper accountMapper;
    private final AccountJpaRepository accountJpaRepository;

    public AccountRepositoryImpl(AccountMapper accountMapper, AccountJpaRepository accountJpaRepository) {
        this.accountMapper = accountMapper;
        this.accountJpaRepository = accountJpaRepository;
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = accountMapper.toEntity(account);
        return accountMapper.toDomainModel(accountJpaRepository.save(entity));
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("accountNumber", match -> match.equals(accountNumber));
        AccountEntity entity = AccountEntity.builder().accountNumber(accountNumber)
                .build();
        Example<AccountEntity> example = Example.of(entity, matcher);
        return accountJpaRepository.findOne(example)
                .map(ac -> accountMapper.toDomainModel(ac));
    }

    @Override
    public List<Account> findAll() {
        return accountJpaRepository.findAll()
                .stream().map(ac -> accountMapper.toDomainModel(ac))
                .toList();
    }

    @Override
    public List<AccountOperation> findAllOperations(String accountNumber) {
        return findByAccountNumber(accountNumber).map(Account::getOperations)
                .orElse(new ArrayList<>());
    }

    @Override
    public Optional<AccountOperation> findOperation(String accountNumber, BigDecimal amount,
            AccountOperationType type) {
        return findByAccountNumber(accountNumber).map(Account::getOperations)
                .orElse(new ArrayList<>())
                .stream().filter(op -> op.getAmount().equals(amount) &&
                        op.getType().equals(type))
                .findFirst();
    }

}
