package infrastructure.database.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;

import infrastructure.database.model.entity.AccountEntity;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {

}