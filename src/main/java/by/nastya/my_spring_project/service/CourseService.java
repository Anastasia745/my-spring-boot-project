package by.nastya.my_spring_project.service;

import by.nastya.my_spring_project.database.entity.Course;
import by.nastya.my_spring_project.database.repository.CourseRepository;
import by.nastya.my_spring_project.database.repository.filters.FilterCourseRepository;
import by.nastya.my_spring_project.dto.create_edit.CourseCreateEditDto;
import by.nastya.my_spring_project.dto.filters.CourseFilter;
import by.nastya.my_spring_project.dto.read.CourseReadDto;
import by.nastya.my_spring_project.mapper.CourseCreateEditMapper;
import by.nastya.my_spring_project.mapper.CourseReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository repository;
    private final CourseReadMapper readMapper;
    private final CourseCreateEditMapper createEditMapper;

    public List<CourseReadDto> findAll() {
        return repository.findAll().stream().map(readMapper::map).toList();
    }

    public Page<CourseReadDto> findAll(CourseFilter filter, Pageable pageable) {
        Specification<Course> specification = FilterCourseRepository.findAllByFilter(filter);
        return repository.findAll(specification, pageable)
                .map(readMapper::map);
    }

    public Optional<CourseReadDto> findById(Integer id) {
        return repository.findById(id)
                .map(readMapper::map);
    }

    public CourseReadDto create(CourseCreateEditDto createDto) {
        return Optional.of(createDto)
                .map(createEditMapper::map)
                .map(repository::save)
                .map(readMapper::map)
                .orElseThrow();
    }

    public Optional<CourseReadDto> update(Integer id, CourseCreateEditDto editDto) {
        return repository.findById(id)
                .map(course -> createEditMapper.map(editDto, course))
                .map(repository::saveAndFlush)
                .map(readMapper::map1);
    }

    public boolean delete(Integer id) {
        return repository.findById(id)
                .map(course -> {
                    repository.delete(course);
                    repository.flush();
                    return true;
                }).orElse(false);
    }
}
