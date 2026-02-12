package by.nastya.my_spring_project.service;

import by.nastya.my_spring_project.database.entity.Student;
import by.nastya.my_spring_project.database.repository.filters.FilterStudentRepository;
import by.nastya.my_spring_project.database.repository.StudentRepository;
import by.nastya.my_spring_project.dto.create_edit.StudentCreateEditDto;
import by.nastya.my_spring_project.dto.filters.StudentFilter;
import by.nastya.my_spring_project.dto.read.StudentReadDto;
import by.nastya.my_spring_project.mapper.StudentCreateEditMapper;
import by.nastya.my_spring_project.mapper.StudentReadMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository repository;
    private final StudentReadMapper readMapper;
    private final StudentCreateEditMapper createEditMapper;

    public Page<StudentReadDto> findAll(StudentFilter filter, Pageable pageable) {
        Specification<Student> specification = FilterStudentRepository.findAllByFilter(filter);
        return repository.findAll(specification, pageable)
                .map(readMapper::map);
    }

    public List<StudentReadDto> findAll() {
        return repository.findAll().stream()
                .map(readMapper::map).toList();
    }

    public List<StudentReadDto> findStudentsWithAvgMarkLessThan(Double minAvgMark) {
        return repository.findStudentsWithAvgMarkLessThan(minAvgMark)
                .stream().map(readMapper::map).toList();
    }

    public Optional<StudentReadDto> findById(Long id) {
        return repository.findById(id)
                .map(readMapper::map);
    }

    public StudentReadDto create(StudentCreateEditDto createDto) {
        return Optional.of(createDto)
                .map(createEditMapper::map)
                .map(repository::save)
                .map(readMapper::map)
                .orElseThrow();
    }

    public Optional<StudentReadDto> update(Long id, StudentCreateEditDto editDto) {
        return repository.findById(id)
                .map(student -> createEditMapper.map(editDto, student))
                .map(repository::saveAndFlush)
                .map(readMapper::map);
    }

    public boolean delete(Long id) {
        return repository.findById(id)
                .map(student -> {
                    repository.delete(student);
                    repository.flush();
                    return true;
                })
                .orElse(false);
    }

}
