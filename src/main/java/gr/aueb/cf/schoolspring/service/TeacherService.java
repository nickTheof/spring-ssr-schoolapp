package gr.aueb.cf.schoolspring.service;

import gr.aueb.cf.schoolspring.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolspring.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolspring.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolspring.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolspring.mapper.Mapper;
import gr.aueb.cf.schoolspring.model.Teacher;
import gr.aueb.cf.schoolspring.model.static_data.Region;
import gr.aueb.cf.schoolspring.repository.IRegionRepository;
import gr.aueb.cf.schoolspring.repository.ITeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TeacherService implements ITeacherService{
    private final ITeacherRepository teacherRepository;
    private final IRegionRepository regionRepository;
    private final Mapper mapper;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TeacherReadOnlyDTO saveTeacher(TeacherInsertDTO dto) throws EntityAlreadyExistsException, EntityInvalidArgumentException {
        if (teacherRepository.findByVat(dto.vat()).isPresent()) {
            throw new EntityAlreadyExistsException("Teacher", "Ο Καθηγητής με το ΑΦΜ " + dto.vat() + " υπάρχει ήδη.");
        }
        if (teacherRepository.findByEmail(dto.email()).isPresent()) {
            throw new EntityAlreadyExistsException("Teacher", "Ο Καθηγητής με το email " + dto.email() + " υπάρχει ήδη.");
        }
        Teacher teacher = mapper.mapToTeacher(dto);
        Region region = regionRepository.findById(dto.regionId()).orElseThrow(
                () -> new EntityInvalidArgumentException("Region", "H περιοχή με id: " + dto.regionId() + " δεν βρέθηκε."));
        region.addTeacher(teacher);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return mapper.mapToTeacherReadOnlyDTO(savedTeacher);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TeacherReadOnlyDTO updateTeacher(Long id, TeacherUpdateDTO dto) throws EntityNotFoundException, EntityInvalidArgumentException, EntityAlreadyExistsException {
        Teacher toUpdate = teacherRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Teacher", "Ο καθηγητής με το id " + id + " δεν βρέθηκε.")
        );
        Optional<Teacher> fetchTeacherByVat = teacherRepository.findByVat(dto.vat());
        if (fetchTeacherByVat.isPresent() && !fetchTeacherByVat.get().getUuid().equals(toUpdate.getUuid()))
            throw new EntityAlreadyExistsException("Teacher", "Ο Καθηγητής με το ΑΦΜ " + dto.vat() + " υπάρχει ήδη.");
        Optional<Teacher> fetchTeacherByEmail = teacherRepository.findByEmail(dto.email());
        if (fetchTeacherByEmail.isPresent() && !fetchTeacherByEmail.get().getUuid().equals(toUpdate.getUuid()))
            throw new EntityAlreadyExistsException("Teacher", "Ο Καθηγητής με το email " + dto.email() + " υπάρχει ήδη.");
        Region region = regionRepository.findById(dto.regionId()).orElseThrow(
                () -> new EntityInvalidArgumentException("Region", "H περιοχή με id: " + dto.regionId() + " δεν βρέθηκε."));
        Teacher teacher = mapper.mapToTeacher(dto, toUpdate);
        if (!region.getId().equals(teacher.getRegion().getId())) {
            teacher.getRegion().removeTeacher(teacher);
            region.addTeacher(teacher);
        }
        Teacher updatedTeacher = teacherRepository.save(teacher);
        return mapper.mapToTeacherReadOnlyDTO(updatedTeacher);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteTeacherByUuid(String uuid) throws EntityNotFoundException {
        Teacher teacher = teacherRepository.findByUuid(uuid).orElseThrow(
                () -> new EntityNotFoundException("Teacher", "Ο καθηγητής με το uuid " + uuid + " δεν βρέθηκε.")
        );
        teacher.getRegion().removeTeacher(teacher);
        teacherRepository.deleteById(teacher.getId());
    }

    @Override
    public TeacherReadOnlyDTO findTeacherByUuid(String uuid) throws EntityNotFoundException {
        Teacher teacher = teacherRepository.findByUuid(uuid).orElseThrow(
                () -> new EntityNotFoundException("Teacher", "Ο καθηγητής με το uuid " + uuid + " δεν βρέθηκε.")
        );
        return mapper.mapToTeacherReadOnlyDTO(teacher);
    }


    @Override
    public Page<TeacherReadOnlyDTO> getPaginatedTeachers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Teacher> teacherPage = teacherRepository.findAll(pageable);
        return teacherPage.map(mapper::mapToTeacherReadOnlyDTO);
    }

}
