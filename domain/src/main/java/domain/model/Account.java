package domain.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Account {
    private String accountNumber;
    private BigDecimal currentBalance;
    private List<Client> owners;
    private List<AccountOperation> operations;
}
