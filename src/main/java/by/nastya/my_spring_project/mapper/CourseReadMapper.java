package by.nastya.my_spring_project.mapper;

import by.nastya.my_spring_project.database.entity.Course;
import by.nastya.my_spring_project.database.entity.CourseTrainer;
import by.nastya.my_spring_project.database.entity.Trainer;
import by.nastya.my_spring_project.dto.read.CourseReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseReadMapper implements Mapper<Course, CourseReadDto> {
//    private final CourseTrainerReadMapper courseTrainerReadMapper;

    @Override
    public CourseReadDto map(Course course) {
//        List<StudentReadDto> students = course.getStudents().stream()
//                .map(studentReadMapper::map).toList();

//        List<CourseTrainerReadDto> courseTrainerList = course.getCourseTrainerList().stream()
//                .map(courseTrainerReadMapper::map).toList();

        List<Trainer> trainers = course.getCourseTrainerList().stream()
                .map(CourseTrainer::getTrainer).toList();

        return new CourseReadDto(
                course.getId(),
                course.getName(),
                course.getStudents(),
                course.getCourseTrainerList(),
                trainers
        );
    }


    public CourseReadDto map1(Course course) {
//        List<StudentReadDto> students = course.getStudents().stream()
//                .map(studentReadMapper::map).toList();

//        List<CourseTrainerReadDto> courseTrainerList = course.getCourseTrainerList().stream()
//                .map(courseTrainerReadMapper::map).toList();

        List<Trainer> trainers = course.getCourseTrainerList().stream()
                .map(CourseTrainer::getTrainer).toList();

        return new CourseReadDto(
                course.getId(),
                course.getName(),
                course.getStudents(),
                course.getCourseTrainerList(),
                trainers
        );
    }
}
