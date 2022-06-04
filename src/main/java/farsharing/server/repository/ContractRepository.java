package farsharing.server.repository;

import farsharing.server.model.entity.ContractEntity;
import farsharing.server.model.entity.enumerate.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, UUID> {

    List<ContractEntity> findAllByStatus(ContractStatus status);
}
