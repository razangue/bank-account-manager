package application.requests;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountOperationRequest {
    private String clientId;
    private String accountNumber;
    private BigDecimal amount;
}
