package farsharing.server.repository;

import farsharing.server.model.entity.CarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, UUID> {

    Optional<CarEntity> findByStateNumber(String stNum);

    Page<CarEntity> findAll(Pageable pageable);
}
