package gr.aueb.cf.schoolspring.repository;

import gr.aueb.cf.schoolspring.core.enums.Role;
import gr.aueb.cf.schoolspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);
    List<User> findByRole(Role role);
    List<User> findByIsActive(Boolean isActive);
    Long countByRole(Role role);
}