package domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountOperation {
    private BigDecimal accountBalanceBeforeOperation;
    private BigDecimal amount;
    private BigDecimal accountBalanceAfterOperation;
    private LocalDateTime date;
    private AccountOperationType type;
}
