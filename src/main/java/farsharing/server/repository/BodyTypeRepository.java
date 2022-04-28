package farsharing.server.repository;

import farsharing.server.model.entity.BodyTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyTypeRepository extends JpaRepository<BodyTypeEntity, String> {
}
