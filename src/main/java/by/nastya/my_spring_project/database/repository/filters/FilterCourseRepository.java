package by.nastya.my_spring_project.database.repository.filters;

import by.nastya.my_spring_project.database.entity.Course;
import by.nastya.my_spring_project.database.entity.CourseTrainer;
import by.nastya.my_spring_project.dto.filters.CourseFilter;
import jakarta.persistence.criteria.*;
import org.aspectj.weaver.ast.Literal;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class FilterCourseRepository {
    public static Specification<Course> likeName(String name) {
        return (course, query, cb) -> cb.like(course.get("name"), name);
    }

    public static Specification<Course> isTrainer(List<String> trainerIds) {
        return (course, query, cb) -> course.join("courseTrainerList").get("trainer").get("id").in(trainerIds);
    }

    public static Specification<Course> alwaysTrue() {
        return (course, query, cb) -> cb.isTrue(cb.literal(true));
    }

    public static Specification<Course> findAllByFilter(CourseFilter filter) {
        Specification<Course> specification = alwaysTrue();
        if (filter.name() != null && !filter.name().isBlank()) {
            specification = specification.and(likeName(filter.name()));
        }
        if (filter.trainerId() != null) {
            specification = specification.and(isTrainer(filter.trainerId()));
        }
        return specification;
    }

}
