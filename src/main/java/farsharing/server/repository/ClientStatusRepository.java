package farsharing.server.repository;

import farsharing.server.model.entity.ClientStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientStatusRepository extends JpaRepository<ClientStatusEntity, String> {
}
