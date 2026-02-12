package by.nastya.my_spring_project.database.repository;

import by.nastya.my_spring_project.database.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Integer> {
}

