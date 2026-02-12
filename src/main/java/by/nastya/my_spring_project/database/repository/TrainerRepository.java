package by.nastya.my_spring_project.database.repository;

import by.nastya.my_spring_project.database.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface TrainerRepository extends JpaRepository<Trainer, Integer>,
        JpaSpecificationExecutor<Trainer> {

}
