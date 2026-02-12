package by.nastya.my_spring_project.controller;

import by.nastya.my_spring_project.database.entity.CourseTrainer;
import by.nastya.my_spring_project.database.repository.PageResponse;
import by.nastya.my_spring_project.database.repository.TrainerRepository;
import by.nastya.my_spring_project.dto.create_edit.TrainerCreateEditDto;
import by.nastya.my_spring_project.dto.filters.TrainerFilter;
import by.nastya.my_spring_project.dto.read.CourseReadDto;
import by.nastya.my_spring_project.dto.read.CourseTrainerReadDto;
import by.nastya.my_spring_project.dto.read.TrainerReadDto;
import by.nastya.my_spring_project.mapper.CourseReadMapper;
import by.nastya.my_spring_project.mapper.TrainerCreateEditMapper;
import by.nastya.my_spring_project.service.CourseService;
import by.nastya.my_spring_project.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("trainers")
public class TrainerController {
    private final TrainerService trainerService;
    private final CourseService courseService;
    private final CourseReadMapper courseReadMapper;

    @GetMapping
    public String findAll(Model model, TrainerFilter filter, Pageable pageable) {
        Page<TrainerReadDto> page = trainerService.findAll(filter, pageable);
        model.addAttribute("trainers", PageResponse.of(page));

        Set<CourseReadDto> courses = new HashSet<>();
        trainerService.findAll().forEach(trainer -> courses.addAll(trainer.getCourses().stream()
                .map(courseReadMapper::map)
                .collect(Collectors.toSet()))
        );
        model.addAttribute("courses", courses);

        model.addAttribute("filter", filter);
        return "trainer/trainers";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        TrainerReadDto trainer = trainerService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("trainer", trainer);

        model.addAttribute("courses", courseService.findAll());

        List<CourseReadDto> trainerCourses = trainer.getCourses().stream()
                .map(courseReadMapper::map).toList();
        model.addAttribute("trainerCourses", trainerCourses);
        return "trainer/trainer";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "trainer/add";
    }

    @PostMapping("/create")
    public String create(TrainerCreateEditDto createDto) {
        TrainerReadDto trainer = trainerService.create(createDto);
        return "redirect:/trainers/" + trainer.getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, TrainerCreateEditDto editDto) {
        return trainerService.update(id, editDto)
                .map(trainer -> "redirect:/trainers/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        trainerService.delete(id);
        return "redirect:/trainers";
    }
}
