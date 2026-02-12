package by.nastya.my_spring_project.dto.read;

import by.nastya.my_spring_project.database.entity.Course;
import by.nastya.my_spring_project.database.entity.CourseTrainer;
import lombok.Value;

import java.util.List;

@Value
public class TrainerReadDto {
    Integer id;
    String firstname;
    String secondname;
    List<CourseTrainer> courseTrainerList;
    List<Course> courses;
}
