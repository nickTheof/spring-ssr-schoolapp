package gr.aueb.cf.schoolspring.mapper;

import gr.aueb.cf.schoolspring.core.enums.Role;
import gr.aueb.cf.schoolspring.dto.*;
import gr.aueb.cf.schoolspring.model.Student;
import gr.aueb.cf.schoolspring.model.Teacher;
import gr.aueb.cf.schoolspring.model.User;
import gr.aueb.cf.schoolspring.model.static_data.Region;
import org.springframework.stereotype.Component;


@Component
public class Mapper {
    public Teacher mapToTeacher(TeacherInsertDTO dto) {
        return new Teacher(null, dto.firstname(), dto.lastname(), dto.vat(), dto.email(),
                dto.fatherName(), dto.street(), dto.streetNum(), dto.zipCode(), null);
    }

    public Teacher mapToTeacher(TeacherUpdateDTO dto, Teacher teacher) {
        teacher.setFirstname(dto.firstname());
        teacher.setLastname(dto.lastname());
        teacher.setVat(dto.vat());
        teacher.setEmail(dto.email());
        teacher.setFatherName(dto.fatherName());
        teacher.setStreet(dto.street());
        teacher.setStreetNum(dto.streetNum());
        teacher.setZipCode(dto.zipCode());
        return teacher;
    }

    public Student mapToStudent(StudentInsertDTO dto) {
        return new Student(null, dto.firstname(), dto.lastname(), dto.vat(), dto.email(),
                dto.fatherName(), dto.street(), dto.streetNum(), dto.zipCode(), null);
    }

    public Student mapToStudent(StudentUpdateDTO dto, Student student) {
        student.setFirstname(dto.firstname());
        student.setLastname(dto.lastname());
        student.setVat(dto.vat());
        student.setEmail(dto.email());
        student.setFatherName(dto.fatherName());
        student.setStreet(dto.street());
        student.setStreetNum(dto.streetNum());
        student.setZipCode(dto.zipCode());
        return student;
    }

    public TeacherReadOnlyDTO mapToTeacherReadOnlyDTO(Teacher teacher) {
        return new TeacherReadOnlyDTO(teacher.getId(), teacher.getUuid(), teacher.getFirstname(), teacher.getLastname(),
                teacher.getVat(), teacher.getEmail(), teacher.getFatherName(), teacher.getStreet(),
                teacher.getStreetNum(), teacher.getZipCode(), teacher.getRegion().getName(), teacher.getCreatedAt(), teacher.getUpdatedAt());
    }

    public StudentReadOnlyDTO mapToStudentReadOnlyDTO(Student student) {
        return new StudentReadOnlyDTO(student.getId(), student.getUuid(), student.getFirstname(), student.getLastname(),
                student.getVat(), student.getEmail(), student.getFatherName(), student.getStreet(),
                student.getStreetNum(), student.getZipCode(), student.getRegion().getName(), student.getCreatedAt(), student.getUpdatedAt());
    }

    public User mapToUser(UserInsertDTO dto) {
        return new User(null, dto.username(), dto.password(), true, Role.READER);
    }

    public User mapToUser(UserInsertByAdminDTO dto) {
        return new User(null, dto.username(), dto.password(), dto.isActive(), Role.valueOf(dto.role()));
    }

    public User mapToUser(UserUpdateDTO dto, User user) {
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setIsActive(dto.isActive());
        user.setRole(Role.valueOf(dto.role()));
        return user;
    }

    public UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
        return new UserReadOnlyDTO(user.getId(), user.getUuid(), user.getUsername(), user.getRole().name(), user.getIsActive(), user.getCreatedAt(), user.getUpdatedAt());
    }

    public RegionReadOnlyDTO mapToRegionReadOnlyDTO(Region region) {
        return new RegionReadOnlyDTO(region.getId(), region.getName());
    }

    public Region mapToRegion(RegionInsertDTO dto) {
        return new Region(null, dto.name(), null, null);
    }
}
