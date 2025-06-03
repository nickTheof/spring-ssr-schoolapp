package gr.aueb.cf.schoolspring.repository;

import gr.aueb.cf.schoolspring.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    List<Student> findByRegionId(Integer id);
    Optional<Student> findByVat(String vat);
    Optional<Student> findByEmail(String email);
    Optional<Student> findByUuid(String uuid);
}
