package by.nastya.my_spring_project.database.repository.filters;

import by.nastya.my_spring_project.database.entity.Student;
import by.nastya.my_spring_project.dto.filters.StudentFilter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class FilterStudentRepository {

    public static Specification<Student> likeFirstname(String firstname) {
        return (student, query, cb) -> cb.like(student.get("firstname"), firstname);
    }

    public static Specification<Student> likeSurname(String surname) {
        return (student, query, cb) -> cb.like(student.get("surname"), surname);
    }

    public static Specification<Student> lessThanBirthdate(LocalDate birthdate) {
        return (student, query, cb) -> cb.lessThan(student.get("birthdate"), birthdate);
    }

    public static Specification<Student> likeCourse(String course) {
        return (student, query, cb) -> cb.like(student.get("course").get("name"), course);
    }

    public static <T> Specification<T> alwaysTrue() {
        return (root, query, cb) -> cb.isTrue(cb.literal(true));
    }

    public static Specification<Student> findAllByFilter(StudentFilter filter) {
        Specification specification = alwaysTrue();
        if (filter.firstname() != null && !filter.firstname().isBlank()) {
            specification = specification.and(likeFirstname(filter.firstname()));
        }
        if (filter.surname() != null && !filter.surname().isBlank()) {
            specification = specification.and(likeSurname(filter.surname()));
        }
        if (filter.birthdate() != null) {
            specification = specification.and(lessThanBirthdate(filter.birthdate()));
        }
        if (filter.course() != null && !filter.course().isBlank()) {
            specification = specification.and(likeCourse(filter.course()));
        }

        return specification;
    }
}
