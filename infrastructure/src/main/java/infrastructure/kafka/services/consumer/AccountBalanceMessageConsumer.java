package infrastructure.kafka.services.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import domain.model.AccountBalanceMessage;
import domain.services.AccountService;

@Component
public class AccountBalanceMessageConsumer {
   private static final Logger logger = LoggerFactory.getLogger(AccountBalanceMessageConsumer.class);
   private final ObjectMapper customObjectMapper;
   private final AccountService accountService;

   public AccountBalanceMessageConsumer(ObjectMapper customObjectMapper,
         AccountService accountService) {
      this.customObjectMapper = customObjectMapper;
      this.accountService = accountService;
   }

   @KafkaListener(topics = "${account.kafka.output.topic.name}", groupId = "${account.kafka.group-id}")
   public void consumeAccountBalanceMessage(ConsumerRecord<String, String> record) {
      String value = record.value();
      try {
         AccountBalanceMessage accountBalanceMessage = customObjectMapper.readValue(value, AccountBalanceMessage.class);
         accountService.updateAccountBalance(accountBalanceMessage);
      } catch (Exception e) {
         logger.error("An error occurs when updating account balance : {}", value);
      }
   }
}
