package application.requests;

import java.math.BigDecimal;

import domain.model.AccountOperationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountOperationSearchRequest extends AccountOperationRequest {
    private AccountOperationType type;

    public AccountOperationSearchRequest(String accountId, String clientId, BigDecimal amount,
            AccountOperationType type) {
        super(accountId, clientId, amount);
        this.type = type;
    }
}
