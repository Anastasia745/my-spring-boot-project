package by.nastya.my_spring_project.database.repository.filters;

import by.nastya.my_spring_project.database.entity.Trainer;
import by.nastya.my_spring_project.dto.filters.TrainerFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class FilterTrainerRepository {

    public static Specification<Trainer> likeFirstname(String firstname) {
        return (trainer, query, cb) -> cb.like(trainer.get("firstname"), firstname);
    }

    public static Specification<Trainer> likeSecondname(String secondname) {
        return (trainer, query, cb) -> cb.like(trainer.get("secondname"), secondname);
    }

    public static Specification<Trainer> isCourse(List<String> courseIds) {
        return (trainer, query, cb) -> trainer.join("courseTrainerList").get("course").get("id").in(courseIds);
    }

    public static Specification<Trainer> alwaysTrue() {
        return (root, query, cb) -> cb.isTrue(cb.literal(true));
    }

    public static Specification<Trainer> findAllByFilter(TrainerFilter filter) {
        Specification<Trainer> specification = alwaysTrue();
        if (filter.firstname() != null && !filter.firstname().isBlank()) {
            specification = specification.and(likeFirstname(filter.firstname()));
        }
        if (filter.secondname() != null && !filter.secondname().isBlank()) {
            specification = specification.and(likeSecondname(filter.secondname()));
        }
        if (filter.courseId() != null) {
            specification = specification.and(isCourse(filter.courseId()));
        }
        return specification;
    }
}
