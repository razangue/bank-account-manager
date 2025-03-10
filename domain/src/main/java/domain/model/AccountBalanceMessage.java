package domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AccountBalanceMessage {
    private String accountNumber;
    private BigDecimal balance;
    private AccountOperation operation;
}
