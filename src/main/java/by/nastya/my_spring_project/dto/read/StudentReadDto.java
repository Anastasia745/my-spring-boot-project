package by.nastya.my_spring_project.dto.read;

import lombok.Value;

import java.time.LocalDate;

@Value
public class StudentReadDto {
    Long id;
    String surname;
    String name;
    LocalDate birthdate;
    CourseReadDto course;
}
