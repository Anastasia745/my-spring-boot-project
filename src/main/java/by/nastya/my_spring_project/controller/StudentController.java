package by.nastya.my_spring_project.controller;

import by.nastya.my_spring_project.database.repository.PageResponse;
import by.nastya.my_spring_project.dto.read.CourseReadDto;
import by.nastya.my_spring_project.dto.create_edit.StudentCreateEditDto;
import by.nastya.my_spring_project.dto.filters.StudentFilter;
import by.nastya.my_spring_project.dto.read.StudentReadDto;
import by.nastya.my_spring_project.service.CourseService;
import by.nastya.my_spring_project.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final CourseService courseService;

    @GetMapping
    public String findAll(Model model,
                          StudentFilter filter,
                          Pageable pageable) {
        Page<StudentReadDto> page = studentService.findAll(filter, pageable);
        model.addAttribute("students", PageResponse.of(page));

        model.addAttribute("filter", filter);

        Set<CourseReadDto> courses = studentService.findAll().stream().map(StudentReadDto::getCourse).collect(Collectors.toSet());
        model.addAttribute("courses", courses);

        String selectedCourse = (filter.course() != null && !filter.course().isBlank()) ? filter.course() : "";
        model.addAttribute("selectedCourse", selectedCourse);

        return "student/students";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("student", studentService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        model.addAttribute("courses", courseService.findAll());
        return "student/student";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "student/add";
    }

    @PostMapping("/create")
    public String create(StudentCreateEditDto createDto) {
        StudentReadDto student = studentService.create(createDto);
        return "redirect:/students/" + student.getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, StudentCreateEditDto editDto) {
        return studentService.update(id, editDto)
                .map(student -> "redirect:/students/{id}")
                .orElseThrow(() -> new ResponseStatusException((HttpStatus.NOT_FOUND)));

    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if(!studentService.delete(id))
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        return "redirect:/students";
    }
}
