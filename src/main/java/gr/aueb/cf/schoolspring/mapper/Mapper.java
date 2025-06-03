package gr.aueb.cf.schoolspring.mapper;

import gr.aueb.cf.schoolspring.core.enums.Role;
import gr.aueb.cf.schoolspring.dto.*;
import gr.aueb.cf.schoolspring.model.Student;
import gr.aueb.cf.schoolspring.model.Teacher;
import gr.aueb.cf.schoolspring.model.User;
import org.springframework.stereotype.Component;


@Component
public class Mapper {
    public Teacher mapToTeacher(TeacherInsertDTO dto) {
        return new Teacher(null, dto.firstname(), dto.lastname(), dto.vat(), dto.email(),
                dto.fatherName(), dto.street(), dto.streetNum(), dto.zipCode(), null);
    }

    public Teacher mapToTeacher(TeacherUpdateDTO dto) {
        return new Teacher(null, dto.firstname(), dto.lastname(), dto.vat(), dto.email(),
                dto.fatherName(), dto.street(), dto.streetNum(), dto.zipCode(), null);
    }

    public Student mapToStudent(StudentInsertDTO dto) {
        return new Student(null, dto.firstname(), dto.lastname(), dto.vat(), dto.email(),
                dto.fatherName(), dto.street(), dto.streetNum(), dto.zipCode(), null);
    }

    public Student mapToStudent(StudentUpdateDTO dto) {
        return new Student(null, dto.firstname(), dto.lastname(), dto.vat(), dto.email(),
                dto.fatherName(), dto.street(), dto.streetNum(), dto.zipCode(), null);
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

    public User mapToUser(UserUpdateDTO dto) {
        return new User(null, dto.username(), dto.password(), dto.isActive(), Role.valueOf(dto.role()));
    }

    public UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
        return new UserReadOnlyDTO(user.getId(), user.getUuid(), user.getUsername(), user.getRole().name(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
