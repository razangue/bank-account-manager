package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"bank.account.manager","application","infrastructure","domain"})
@EntityScan(basePackages = {"bank.account.manager","application","infrastructure","domain"})
public class BankAccountManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAccountManagerApplication.class, args);
	}
}
