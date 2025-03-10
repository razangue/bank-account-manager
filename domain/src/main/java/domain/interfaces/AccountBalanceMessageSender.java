package domain.interfaces;

import domain.model.AccountBalanceMessage;

public interface AccountBalanceMessageSender {
    void sendAccountBalanceMessage(AccountBalanceMessage message);
}
