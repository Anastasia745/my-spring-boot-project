package by.nastya.my_spring_project.mapper;

import by.nastya.my_spring_project.database.entity.Course;
import by.nastya.my_spring_project.database.entity.CourseTrainer;
import by.nastya.my_spring_project.database.entity.Student;
import by.nastya.my_spring_project.database.entity.Trainer;
import by.nastya.my_spring_project.database.repository.CourseRepository;
import by.nastya.my_spring_project.database.repository.CourseTrainerRepository;
import by.nastya.my_spring_project.database.repository.StudentRepository;
import by.nastya.my_spring_project.database.repository.TrainerRepository;
import by.nastya.my_spring_project.dto.create_edit.CourseCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CourseCreateEditMapper implements Mapper<CourseCreateEditDto, Course> {
    private final TrainerRepository trainerRepository;

    @Override
    public Course map(CourseCreateEditDto dto) {
        List<Trainer> trainers = getTrainers(dto.getTrainerIds());

        Course course = new Course(dto.getId(),
                dto.getName(),
                new ArrayList<>(),
                new ArrayList<>());
        setCourseTrainerList(course, trainers);
        return course;
    }

    public Course map(CourseCreateEditDto dto, Course course) {
        course.setName(dto.getName());
        setCourseTrainerList(course, getTrainers(dto.getTrainerIds()));
        return course;
    }

    public List<Trainer> getTrainers(List<Integer> trainerIds) {
        return trainerIds.stream()
                .map(id -> trainerRepository.findById(id).orElse(null))
                .toList();
    }

    public void setCourseTrainerList(Course course, List<Trainer> trainers) {
        List<Integer> courseTrainerIds = course.getCourseTrainerList().stream()
                .map(CourseTrainer::getTrainer)
                .map(Trainer::getId).toList();
        List<Integer> trainerIds = trainers.stream().map(Trainer::getId).toList();

        List<CourseTrainer> copyCourseTrainer = new ArrayList<>(course.getCourseTrainerList());

        course.getCourseTrainerList().removeIf(courseTrainer ->
                !trainerIds.contains(courseTrainer.getTrainer().getId())
        );

        copyCourseTrainer.forEach(courseTrainer -> {
            Trainer trainer = courseTrainer.getTrainer();
            if (!trainerIds.contains(trainer.getId())) {
                trainer.getCourseTrainerList().remove(courseTrainer);
            }
        });

        trainers.forEach(trainer -> {
            if (!courseTrainerIds.contains(trainer.getId())) {
                CourseTrainer courseTrainer = new CourseTrainer();
                courseTrainer.setCourse(course);
                courseTrainer.setTrainer(trainer);
            }
        });
    }
}
