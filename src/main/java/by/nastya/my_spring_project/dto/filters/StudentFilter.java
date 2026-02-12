package by.nastya.my_spring_project.dto.filters;

import by.nastya.my_spring_project.database.entity.Course;

import java.time.LocalDate;

public record StudentFilter(String firstname,
                            String surname,
                            LocalDate birthdate,
                            String course) {
}
