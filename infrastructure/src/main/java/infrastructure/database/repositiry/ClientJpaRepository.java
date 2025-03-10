package infrastructure.database.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;

import infrastructure.database.model.entity.ClientEntity;

public interface ClientJpaRepository extends JpaRepository<ClientEntity, Long> {

}
