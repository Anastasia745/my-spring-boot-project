package by.nastya.my_spring_project.mapper;

public interface Mapper<T, R> {

    R map(T fromObject);
}
