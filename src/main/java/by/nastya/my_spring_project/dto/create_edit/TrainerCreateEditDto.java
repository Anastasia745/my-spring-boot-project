package by.nastya.my_spring_project.dto.create_edit;

import lombok.Value;

import java.util.List;

@Value
public class TrainerCreateEditDto {
    Integer id;
    String firstname;
    String secondname;
    List<Integer> courseIds;
}
