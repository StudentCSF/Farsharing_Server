package farsharing.server.repository;

import farsharing.server.model.entity.ContractStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractStatusRepository extends JpaRepository<ContractStatusEntity, String> {
}
