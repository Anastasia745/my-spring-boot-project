package by.nastya.my_spring_project.controller;

import by.nastya.my_spring_project.database.entity.Course;
import by.nastya.my_spring_project.database.entity.CourseTrainer;
import by.nastya.my_spring_project.database.entity.Trainer;
import by.nastya.my_spring_project.database.repository.PageResponse;
import by.nastya.my_spring_project.dto.create_edit.CourseCreateEditDto;
import by.nastya.my_spring_project.dto.filters.CourseFilter;
import by.nastya.my_spring_project.dto.read.CourseReadDto;
import by.nastya.my_spring_project.dto.read.TrainerReadDto;
import by.nastya.my_spring_project.mapper.TrainerReadMapper;
import by.nastya.my_spring_project.service.CourseService;
import by.nastya.my_spring_project.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final TrainerService trainerService;
    private final TrainerReadMapper trainerMapper;

    @GetMapping
    public String findAll(Model model, CourseFilter filter, Pageable pageable) {
        Page<CourseReadDto> page = courseService.findAll(filter, pageable);
        Set<TrainerReadDto> trainers = new HashSet<>();
        courseService.findAll().forEach(course -> trainers.addAll(course.getTrainers().stream()
                        .map(trainerMapper::map)
                        .collect(Collectors.toSet()))
                );
        model.addAttribute("trainers", trainers);
        model.addAttribute("courses", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "course/courses";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        CourseReadDto course = courseService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("course", course);

        List<TrainerReadDto> courseTrainers = course.getTrainers().stream().map(trainerMapper::map).toList();
        model.addAttribute("courseTrainers", courseTrainers);

        model.addAttribute("trainers", trainerService.findAll());
        return "course/course";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("trainers", trainerService.findAll());
        return "course/add";
    }

    @PostMapping("/create")
    public String create(CourseCreateEditDto createDto) {
        CourseReadDto course = courseService.create(createDto);
        return "redirect:/courses/" + course.getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, CourseCreateEditDto editDto) {
        return courseService.update(id, editDto)
                .map(course -> "redirect:/courses/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        courseService.delete(id);
        return "redirect:/courses";
    }
}
