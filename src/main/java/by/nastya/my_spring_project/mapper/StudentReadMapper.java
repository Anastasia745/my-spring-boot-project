package by.nastya.my_spring_project.mapper;

import by.nastya.my_spring_project.database.entity.Student;
import by.nastya.my_spring_project.dto.read.CourseReadDto;
import by.nastya.my_spring_project.dto.read.StudentReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StudentReadMapper implements Mapper<Student, StudentReadDto> {
    private final CourseReadMapper courseReadMapper;

    @Override
    public StudentReadDto map(Student student) {
        CourseReadDto course = Optional.of(student.getCourse())
                .map(courseReadMapper::map)
                .orElse(null);

        return new StudentReadDto(
                student.getId(),
                student.getSurname(),
                student.getFirstname(),
                student.getBirthdate(),
                course
        );
    }
}
