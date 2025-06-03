package gr.aueb.cf.schoolspring.repository;

import gr.aueb.cf.schoolspring.core.enums.Role;
import gr.aueb.cf.schoolspring.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUuid(String uuid);
    Page<User> findByRole(Role role, Pageable pageable);
    Page<User> findByIsActive(Boolean isActive, Pageable pageable);
    Long countByRole(Role role);
}