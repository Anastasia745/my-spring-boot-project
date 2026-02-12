package by.nastya.my_spring_project.database.repository;

import by.nastya.my_spring_project.database.entity.Course;
import by.nastya.my_spring_project.database.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>,
        JpaSpecificationExecutor<Course> {

}
