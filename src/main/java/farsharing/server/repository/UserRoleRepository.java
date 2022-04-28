package farsharing.server.repository;

import farsharing.server.model.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String> {


    @Modifying
    @Query(value="update UserRoleEntity set name = ?1 where name = ?2")
    @Transactional
    void setNameOfUserRole(String newName, String oldName);
}
