package io.github.apedrina.web.repository;

import io.github.apedrina.web.model.ERole;
import io.github.apedrina.web.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
