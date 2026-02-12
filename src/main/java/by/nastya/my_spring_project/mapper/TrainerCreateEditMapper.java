package by.nastya.my_spring_project.mapper;

import by.nastya.my_spring_project.database.entity.Course;
import by.nastya.my_spring_project.database.entity.CourseTrainer;
import by.nastya.my_spring_project.database.entity.Trainer;
import by.nastya.my_spring_project.database.repository.CourseRepository;
import by.nastya.my_spring_project.database.repository.CourseTrainerRepository;
import by.nastya.my_spring_project.dto.create_edit.TrainerCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TrainerCreateEditMapper implements Mapper<TrainerCreateEditDto, Trainer>{
    private final CourseRepository courseRepository;
    private final CourseTrainerRepository courseTrainerRepository;

    @Override
    public Trainer map(TrainerCreateEditDto dto) {
        List<Course> courses = getCourses(dto.getCourseIds());

        Trainer trainer = new Trainer(dto.getId(),
                dto.getFirstname(),
                dto.getSecondname(),
                new ArrayList<>());
        setCourseTrainerList(trainer, courses);
        return trainer;
    }

    public Trainer map(TrainerCreateEditDto dto, Trainer trainer) {
        trainer.setFirstname(dto.getFirstname());
        trainer.setSecondname(dto.getSecondname());
        setCourseTrainerList(trainer, getCourses(dto.getCourseIds()));
        return trainer;
    }

    public List<Course> getCourses(List<Integer> courseIds) {
        return courseIds.stream()
                .map(id -> courseRepository.findById(id).orElse(null))
                .toList();
    }

    public void setCourseTrainerList(Trainer trainer, List<Course> courses) {
        List<Integer> trainerCourseIds = trainer.getCourseTrainerList().stream()
                .map(CourseTrainer::getCourse)
                .map(Course::getId).toList();
        List<Integer> courseIds = courses.stream().map(Course::getId).toList();

        List<CourseTrainer> copyCourseTrainer = new ArrayList<>(trainer.getCourseTrainerList());

        trainer.getCourseTrainerList().removeIf(courseTrainer ->
            !courseIds.contains(courseTrainer.getCourse().getId())
        );

        copyCourseTrainer.forEach(courseTrainer -> {
            Course course = courseTrainer.getCourse();
            if (!courseIds.contains(course.getId())) {
                course.getCourseTrainerList().remove(courseTrainer);
            }
        });

        courses.forEach(course -> {
            if (!trainerCourseIds.contains(course.getId())) {
                CourseTrainer courseTrainer = new CourseTrainer();
                courseTrainer.setCourse(course);
                courseTrainer.setTrainer(trainer);
            }
        });
    }
}
