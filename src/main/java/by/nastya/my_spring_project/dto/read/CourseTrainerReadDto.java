package by.nastya.my_spring_project.dto.read;

import by.nastya.my_spring_project.database.entity.Course;
import by.nastya.my_spring_project.database.entity.CourseTrainer;
import by.nastya.my_spring_project.database.entity.Student;
import by.nastya.my_spring_project.database.entity.Trainer;
import lombok.Value;

import java.util.List;

@Value
public class CourseTrainerReadDto {
    Integer id;
    Course course;
    Trainer trainer;
}
