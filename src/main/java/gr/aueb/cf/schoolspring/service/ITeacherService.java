package gr.aueb.cf.schoolspring.service;

import gr.aueb.cf.schoolspring.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolspring.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolspring.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolspring.dto.TeacherUpdateDTO;
import org.springframework.data.domain.Page;


public interface ITeacherService {
    TeacherReadOnlyDTO saveTeacher(TeacherInsertDTO dto)
            throws EntityAlreadyExistsException, EntityInvalidArgumentException;
    TeacherReadOnlyDTO updateTeacher(String uuid, TeacherUpdateDTO dto)
        throws EntityNotFoundException, EntityInvalidArgumentException, EntityAlreadyExistsException;
    void deleteTeacherByUuid(String uuid) throws EntityNotFoundException;
    TeacherReadOnlyDTO findTeacherByUuid(String uuid) throws EntityNotFoundException;
    Page<TeacherReadOnlyDTO> getPaginatedTeachers(int page, int size);
}

