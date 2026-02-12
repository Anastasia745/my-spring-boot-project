package by.nastya.my_spring_project.dto.create_edit;
import lombok.Value;
import java.time.LocalDate;
import java.util.List;

@Value
public class StudentCreateEditDto {

    Long id;
    String firstname;
    String surname;
    LocalDate birthdate;
    Integer courseId;
//    List<Integer> studentProfileIds;
}
