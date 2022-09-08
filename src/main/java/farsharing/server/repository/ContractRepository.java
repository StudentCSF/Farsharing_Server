package farsharing.server.repository;

import farsharing.server.model.entity.ContractEntity;
import farsharing.server.model.entity.enumerate.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, UUID> {

//    List<ContractEntity> findAllByStatus(ContractStatus status);
//
//    Page<ContractEntity> findAll(Pageable pageable);

    @Query(nativeQuery = true,
            value = "select * from contract where car_uid=:uid order by start_time desc;")
    List<ContractEntity> findByCar(UUID uid);

    List<ContractEntity> findAllByClientUidAndStatus(UUID uid, ContractStatus status);
}
