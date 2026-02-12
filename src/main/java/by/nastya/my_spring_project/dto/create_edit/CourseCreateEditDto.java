package by.nastya.my_spring_project.dto.create_edit;

import lombok.Value;

import java.util.List;

@Value
public class CourseCreateEditDto {
    Integer id;
    String name;
    List<Integer> trainerIds;
}
