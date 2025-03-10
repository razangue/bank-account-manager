package domain.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import domain.exception.EntityNotFoundException;
import domain.interfaces.AccountBalanceMessageSender;
import domain.interfaces.AccountOperationRepository;
import domain.interfaces.AccountRepository;
import domain.model.Account;
import domain.model.AccountBalanceMessage;
import domain.model.AccountOperation;
import domain.model.AccountOperationType;

@Component
public class AccountOperationService {
  private final AccountBalanceMessageSender accountBalanceMessageSender;
  private final AccountRepository accountRepository;
  private final AccountOperationRepository accountOperationRepository;

  public AccountOperationService(AccountBalanceMessageSender accountBalanceMessageSender,
      AccountRepository accountRepository, AccountOperationRepository accountOperationRepository) {
    this.accountBalanceMessageSender = accountBalanceMessageSender;
    this.accountRepository = accountRepository;
    this.accountOperationRepository = accountOperationRepository;
  }

  public void makeDeposit(String accountNumber, BigDecimal amount) {
    Account account = accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new EntityNotFoundException(Account.class, accountNumber));
    if (isDepositPossible(account, amount)) {
      BigDecimal currentBalance = account.getCurrentBalance();
      BigDecimal balanceAfterOperation = currentBalance.add(amount);
      AccountOperation depositOperation = AccountOperation.builder()
          .accountBalanceBeforeOperation(currentBalance)
          .accountBalanceAfterOperation(balanceAfterOperation)
          .amount(amount)
          .date(LocalDateTime.now())
          .type(AccountOperationType.DEPOSIT)
          .build();
      accountBalanceMessageSender.sendAccountBalanceMessage(AccountBalanceMessage.builder()
          .accountNumber(account.getAccountNumber())
          .balance(balanceAfterOperation)
          .operation(depositOperation)
          .build());
    }
  }

  public void makeWithdrawal(String accountNumber, BigDecimal amount) {
    Account account = accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new EntityNotFoundException(Account.class, accountNumber));
    if (isWithdrawalPossible(account, amount)) {
      BigDecimal currentBalance = account.getCurrentBalance();
      BigDecimal balanceAfterOperation = currentBalance.subtract(amount);
      AccountOperation withdrawalOperation = AccountOperation.builder()
          .accountBalanceBeforeOperation(currentBalance)
          .accountBalanceAfterOperation(balanceAfterOperation)
          .amount(amount)
          .date(LocalDateTime.now())
          .type(AccountOperationType.WITHDRAWAL)
          .build();
      accountBalanceMessageSender.sendAccountBalanceMessage(AccountBalanceMessage.builder()
          .accountNumber(account.getAccountNumber())
          .balance(balanceAfterOperation)
          .operation(withdrawalOperation)
          .build());
    }
  }

  public List<AccountOperation> findAllOperations(String accountNumber) {
    return accountRepository.findAllOperations(accountNumber);
  }

  public Optional<AccountOperation> findOperation(String accountNumber, BigDecimal amount,
      AccountOperationType type) {
    return accountRepository.findOperation(accountNumber, amount, type);
  }

  public boolean isDepositPossible(Account account, BigDecimal amount) {
    return account != null && amount != null && amount.compareTo(BigDecimal.ZERO) >= 0;
  }

  public boolean isWithdrawalPossible(Account account, BigDecimal amount) {
    BigDecimal currentBalance = account.getCurrentBalance();
    return isDepositPossible(account, amount) && currentBalance != null
        && currentBalance.compareTo(amount) >= 0;
  }
}
