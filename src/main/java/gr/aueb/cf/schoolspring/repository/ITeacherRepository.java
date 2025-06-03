package gr.aueb.cf.schoolspring.repository;

import gr.aueb.cf.schoolspring.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ITeacherRepository extends JpaRepository<Teacher, Long>, JpaSpecificationExecutor<Teacher> {
    List<Teacher> findByRegionId(Integer id);
    Optional<Teacher> findByVat(String vat);
    Optional<Teacher> findByEmail(String email);
    Optional<Teacher> findByUuid(String uuid);
}
