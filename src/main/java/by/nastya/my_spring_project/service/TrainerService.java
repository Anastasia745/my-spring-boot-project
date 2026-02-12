package by.nastya.my_spring_project.service;

import by.nastya.my_spring_project.database.entity.Trainer;
import by.nastya.my_spring_project.database.repository.TrainerRepository;
import by.nastya.my_spring_project.database.repository.filters.FilterTrainerRepository;
import by.nastya.my_spring_project.dto.create_edit.TrainerCreateEditDto;
import by.nastya.my_spring_project.dto.filters.TrainerFilter;
import by.nastya.my_spring_project.dto.read.TrainerReadDto;
import by.nastya.my_spring_project.mapper.TrainerCreateEditMapper;
import by.nastya.my_spring_project.mapper.TrainerReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerRepository repository;
    private final TrainerReadMapper readMapper;
    private final TrainerCreateEditMapper createEditMapper;

    public Page<TrainerReadDto> findAll(TrainerFilter filter, Pageable pageable) {
        Specification<Trainer> specification = FilterTrainerRepository.findAllByFilter(filter);
        return repository.findAll(specification, pageable)
                .map(readMapper::map);
    }

    public List<TrainerReadDto> findAll() {
        return repository.findAll().stream()
                .map(readMapper::map).toList();
    }

    public Optional<TrainerReadDto> findById(Integer id) {
        return repository.findById(id)
                .map(readMapper::map);
    }

    public TrainerReadDto create(TrainerCreateEditDto createDto) {
        return Optional.of(createDto)
                .map(createEditMapper::map)
                .map(repository::save)
                .map(readMapper::map)
                .orElse(null);
    }

    public Optional<TrainerReadDto> update(Integer id, TrainerCreateEditDto editDto) {
        return repository.findById(id)
                .map(trainer -> createEditMapper.map(editDto, trainer))
                .map(repository::save)
                .map(readMapper::map);
    }

    public boolean delete(Integer id) {
        return repository.findById(id)
                .map(trainer -> {
                    repository.delete(trainer);
                    repository.flush();
                    return true;
                })
                .orElse(false);
    }
}
