package obss.project.finalproject.repository;

import obss.project.finalproject.model.Role;
import obss.project.finalproject.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(RoleType name);


}
