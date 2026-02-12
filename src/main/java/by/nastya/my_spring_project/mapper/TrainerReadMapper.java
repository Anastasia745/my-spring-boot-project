package by.nastya.my_spring_project.mapper;

import by.nastya.my_spring_project.database.entity.Course;
import by.nastya.my_spring_project.database.entity.CourseTrainer;
import by.nastya.my_spring_project.database.entity.Trainer;
import by.nastya.my_spring_project.dto.read.CourseReadDto;
import by.nastya.my_spring_project.dto.read.CourseTrainerReadDto;
import by.nastya.my_spring_project.dto.read.TrainerReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrainerReadMapper implements Mapper<Trainer, TrainerReadDto>{
//    CourseTrainerReadMapper courseTrainerReadMapper;

    @Override
    public TrainerReadDto map(Trainer trainer) {
//        List<CourseTrainerReadDto> courseTrainerList = trainer.getCourseTrainerList().stream()
//                .map(courseTrainerReadMapper::map).toList();

        List<Course> courses = trainer.getCourseTrainerList().stream()
                .map(CourseTrainer::getCourse).toList();

        return new TrainerReadDto(trainer.getId(),
                trainer.getFirstname(),
                trainer.getSecondname(),
                trainer.getCourseTrainerList(),
                courses
        );
    }
}
