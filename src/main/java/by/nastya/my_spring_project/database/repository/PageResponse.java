package by.nastya.my_spring_project.database.repository;

import lombok.Value;
import org.springframework.data.domain.Page;
import java.util.List;

@Value
public class PageResponse<T> {
    List<T> content;
    int number;
    int size;
    int totalCount;

    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalPages());
    }
}
