package by.nastya.my_spring_project.dto.filters;

import java.util.List;

public record TrainerFilter(String firstname,
                            String secondname,
                            List<String> courseId) {
}
