package infrastructure.database.model.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

@Entity
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private BigDecimal currentBalance;
    @OneToMany(mappedBy = "owners", fetch = FetchType.EAGER)
    private List<ClientEntity> owners;
    @OneToMany(mappedBy = "operations", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountOperationEntity> operations;
}
