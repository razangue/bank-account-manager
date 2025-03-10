package infrastructure.database.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;

import infrastructure.database.model.entity.AccountOperationEntity;

public interface AccountOperationJpaRepository extends JpaRepository<AccountOperationEntity, Long> {

}