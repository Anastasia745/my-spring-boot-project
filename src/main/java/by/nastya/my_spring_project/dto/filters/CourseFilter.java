package by.nastya.my_spring_project.dto.filters;

import java.util.List;

public record CourseFilter(String name,
                           List<String> trainerId) {
}
