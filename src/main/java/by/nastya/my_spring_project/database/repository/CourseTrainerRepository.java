package by.nastya.my_spring_project.database.repository;

import by.nastya.my_spring_project.database.entity.Course;
import by.nastya.my_spring_project.database.entity.CourseTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseTrainerRepository extends JpaRepository<CourseTrainer, Integer> {
}
