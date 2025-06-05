package gr.aueb.cf.schoolspring.service;

import gr.aueb.cf.schoolspring.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolspring.dto.StudentInsertDTO;
import gr.aueb.cf.schoolspring.dto.StudentReadOnlyDTO;
import gr.aueb.cf.schoolspring.dto.StudentUpdateDTO;
import org.springframework.data.domain.Page;


public interface IStudentService {
    StudentReadOnlyDTO saveStudent(StudentInsertDTO dto)
        throws EntityAlreadyExistsException, EntityInvalidArgumentException;
    StudentReadOnlyDTO updateStudent(String uuid, StudentUpdateDTO dto)
        throws EntityAlreadyExistsException, EntityInvalidArgumentException, EntityNotFoundException;
    void deleteStudentByUuid(String uuid) throws EntityNotFoundException;
    Page<StudentReadOnlyDTO> getPaginatedStudents(int page, int size);
    StudentReadOnlyDTO findStudentByUuid(String uuid) throws EntityNotFoundException;
}
