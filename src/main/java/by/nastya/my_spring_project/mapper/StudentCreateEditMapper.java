package by.nastya.my_spring_project.mapper;

import by.nastya.my_spring_project.database.entity.Course;
import by.nastya.my_spring_project.database.entity.Student;
import by.nastya.my_spring_project.database.entity.StudentProfile;
import by.nastya.my_spring_project.database.repository.CourseRepository;
import by.nastya.my_spring_project.database.repository.StudentProfileRepository;
import by.nastya.my_spring_project.dto.create_edit.StudentCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StudentCreateEditMapper implements Mapper<StudentCreateEditDto, Student> {
    private final CourseRepository courseRepository;
    private final StudentProfileRepository studentProfileRepository;

    @Override
    public Student map(StudentCreateEditDto dto) {
        Course course = getCourse(dto.getCourseId());
//        List<StudentProfile> marks = getMarks(dto.getStudentProfileIds());
        return new Student(
                dto.getId(),
                dto.getSurname(),
                dto.getFirstname(),
                dto.getBirthdate(),
                course,
                new ArrayList<>()
        );
    }

    public Student map(StudentCreateEditDto dto, Student student) {
        Course course = getCourse(student.getCourse().getId());
//        List<StudentProfile> marks = getMarks(dto.getStudentProfileIds());
        student.setFirstname(dto.getFirstname());
        student.setSurname(dto.getSurname());
        student.setBirthdate(dto.getBirthdate());
        student.setCourse(course);
//        student.setMarks(marks);
        return student;
    }

    public Course getCourse(Integer id) {
        return Optional.of(id)
                .flatMap(courseRepository::findById)
                .orElse(null);
    }

    public List<StudentProfile> getMarks(List<Integer> studentProfileIds) {
        return studentProfileIds.stream()
                .map(id -> studentProfileRepository.findById(id)
                        .orElse(null))
                .toList();
    }
}
