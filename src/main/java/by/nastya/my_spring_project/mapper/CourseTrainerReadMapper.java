package by.nastya.my_spring_project.mapper;

import by.nastya.my_spring_project.database.entity.CourseTrainer;
import by.nastya.my_spring_project.dto.read.CourseReadDto;
import by.nastya.my_spring_project.dto.read.CourseTrainerReadDto;
import by.nastya.my_spring_project.dto.read.TrainerReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CourseTrainerReadMapper implements Mapper<CourseTrainer, CourseTrainerReadDto> {
//    private final CourseReadMapper courseReadMapper;
//    private final TrainerReadMapper trainerReadMapper;

    @Override
    public CourseTrainerReadDto map(CourseTrainer courseTrainer) {
//        CourseReadDto course = Optional.of(courseTrainer.getCourse())
//                .map(courseReadMapper::map).orElse(null);
//        TrainerReadDto trainer = Optional.of(courseTrainer.getTrainer())
//                .map(trainerReadMapper::map).orElse(null);

        return new CourseTrainerReadDto(courseTrainer.getId(),
                courseTrainer.getCourse(),
                courseTrainer.getTrainer()
        );
    }
}
