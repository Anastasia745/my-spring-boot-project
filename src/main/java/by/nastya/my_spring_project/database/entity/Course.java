package by.nastya.my_spring_project.database.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    private List<Student> students;

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
//    @JoinTable(name = "courses_trainers",
//            joinColumns = @JoinColumn(name = "trainer_id"),
//            inverseJoinColumns = @JoinColumn(name = "course_id"))
//    private List<Course> trainers;

//    @Builder.Default
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseTrainer> courseTrainerList;

    public void setStudent(Student student) {
        students.add(student);
        student.setCourse(this);
    }
}
