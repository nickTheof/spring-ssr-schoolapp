package gr.aueb.cf.schoolspring.model.static_data;

import gr.aueb.cf.schoolspring.model.Student;
import gr.aueb.cf.schoolspring.model.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "regions")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Getter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private Set<Teacher> teachers = new HashSet<>();

    @Getter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();

    public Set<Teacher> getAllTeachers() {
        return Collections.unmodifiableSet(teachers);
    }

    public Set<Student> getAllStudents() {
        return Collections.unmodifiableSet(students);
    }

    public void addTeacher(Teacher teacher) {
        if (teacher == null) return;
        if (teachers == null) teachers = new HashSet<>();
        teachers.add(teacher);
        teacher.setRegion(this);
    }

    public void removeTeacher(Teacher teacher) {
        if (teachers == null || teacher == null) return;
        teachers.remove(teacher);
        teacher.setRegion(null);
    }

    public void addStudent(Student student) {
        if (student == null) return;
        if (students == null) students = new HashSet<>();
        students.add(student);
        student.setRegion(this);
    }

    public void removeStudent(Student student) {
        if (students == null || student == null) return;
        students.remove(student);
        student.setRegion(null);
    }


}
