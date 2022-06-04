package farsharing.server.repository;

import farsharing.server.model.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {

    Optional<ClientEntity> findByLicense(String license);

    Optional<ClientEntity> findByUserUid(UUID uid);
}
