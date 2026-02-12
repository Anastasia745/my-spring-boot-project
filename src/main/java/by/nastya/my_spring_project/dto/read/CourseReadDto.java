package by.nastya.my_spring_project.dto.read;

import by.nastya.my_spring_project.database.entity.CourseTrainer;
import by.nastya.my_spring_project.database.entity.Student;
import by.nastya.my_spring_project.database.entity.Trainer;
import lombok.Value;

import java.util.List;

@Value
public class CourseReadDto {
    Integer id;
    String name;
    List<Student> students;
    List<CourseTrainer> courseTrainerList;
    List<Trainer> trainers;
}
