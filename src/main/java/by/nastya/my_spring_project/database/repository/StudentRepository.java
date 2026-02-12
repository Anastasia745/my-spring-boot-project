package by.nastya.my_spring_project.database.repository;

import by.nastya.my_spring_project.database.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>,
        JpaSpecificationExecutor<Student> {

    @Query(value = "SELECT sp.student FROM StudentProfile sp GROUP BY sp.student HAVING avg(sp.mark) < :minAvgMark", nativeQuery = false)
    List<Student> findStudentsWithAvgMarkLessThan(Double minAvgMark);
}
