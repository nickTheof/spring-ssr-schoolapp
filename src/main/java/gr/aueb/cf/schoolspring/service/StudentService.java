package gr.aueb.cf.schoolspring.service;

import gr.aueb.cf.schoolspring.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolspring.dto.StudentInsertDTO;
import gr.aueb.cf.schoolspring.dto.StudentReadOnlyDTO;
import gr.aueb.cf.schoolspring.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolspring.mapper.Mapper;
import gr.aueb.cf.schoolspring.model.Student;
import gr.aueb.cf.schoolspring.model.static_data.Region;
import gr.aueb.cf.schoolspring.repository.IRegionRepository;
import gr.aueb.cf.schoolspring.repository.IStudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final IStudentRepository studentRepository;
    private final IRegionRepository regionRepository;
    private final Mapper mapper;

    @Override
    public StudentReadOnlyDTO saveStudent(StudentInsertDTO dto) throws EntityAlreadyExistsException, EntityInvalidArgumentException {
        if (studentRepository.findByVat(dto.vat()).isPresent()) {
            throw new EntityAlreadyExistsException("Student", "Ο Μαθητής με το ΑΦΜ " + dto.vat() + " υπάρχει ήδη.");
        }
        if (studentRepository.findByEmail(dto.email()).isPresent()) {
            throw new EntityAlreadyExistsException("Student", "Ο Μαθητής με το email " + dto.email() + " υπάρχει ήδη.");
        }
        Student student = mapper.mapToStudent(dto);
        Region region = regionRepository.findById(dto.regionId()).orElseThrow(
                () -> new EntityInvalidArgumentException("Region", "H περιοχή με id: " + dto.regionId() + " δεν βρέθηκε."));
        region.addStudent(student);
        Student savedStudent = studentRepository.save(student);
        return mapper.mapToStudentReadOnlyDTO(savedStudent);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public StudentReadOnlyDTO updateStudent(String uuid, StudentUpdateDTO dto) throws EntityAlreadyExistsException, EntityInvalidArgumentException, EntityNotFoundException {
        Student toUpdate = studentRepository.findByUuid(uuid).orElseThrow(
                () -> new EntityNotFoundException("Student", "Ο μαθητής με το uuid " + uuid + " δεν βρέθηκε.")
        );
        Optional<Student> fetchStudentByVat = studentRepository.findByVat(dto.vat());
        if (fetchStudentByVat.isPresent() && !fetchStudentByVat.get().getUuid().equals(toUpdate.getUuid()))
            throw new EntityAlreadyExistsException("Student", "Ο μαθητής με το ΑΦΜ " + dto.vat() + " υπάρχει ήδη.");
        Optional<Student> fetchStudentByEmail = studentRepository.findByEmail(dto.email());
        if (fetchStudentByEmail.isPresent() && !fetchStudentByEmail.get().getUuid().equals(toUpdate.getUuid()))
            throw new EntityAlreadyExistsException("Student", "Ο μαθητής με το email " + dto.email() + " υπάρχει ήδη.");
        Region region = regionRepository.findById(dto.regionId()).orElseThrow(
                () -> new EntityInvalidArgumentException("Region", "H περιοχή με id: " + dto.regionId() + " δεν βρέθηκε."));
        Student student = mapper.mapToStudent(dto, toUpdate);
        if (!region.getId().equals(student.getRegion().getId())) {
            student.getRegion().removeStudent(student);
            region.addStudent(student);
        }
        Student updatedStudent = studentRepository.save(student);
        return mapper.mapToStudentReadOnlyDTO(updatedStudent);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteStudentByUuid(String uuid) throws EntityNotFoundException {
        Student student = studentRepository.findByUuid(uuid).orElseThrow(
                () -> new EntityNotFoundException("Student", "Ο μαθητής με το uuid " + uuid + " δεν βρέθηκε.")
        );
        student.getRegion().removeStudent(student);
        studentRepository.deleteById(student.getId());
    }

    @Override
    public Page<StudentReadOnlyDTO> getPaginatedStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> students = studentRepository.findAll(pageable);
        return students.map(mapper::mapToStudentReadOnlyDTO);
    }

    @Override
    public StudentReadOnlyDTO findStudentByUuid(String uuid) throws EntityNotFoundException {
        Student student = studentRepository.findByUuid(uuid).orElseThrow(
                () -> new EntityNotFoundException("Student", "Ο μαθητής με το uuid " + uuid + " δεν βρέθηκε.")
        );
        return mapper.mapToStudentReadOnlyDTO(student);
    }
}
