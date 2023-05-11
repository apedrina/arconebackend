package io.github.apedrina.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, length = 30)
    private String name;

    @Column(name = "description", unique = true, length = 60)
    private String description;

    @ManyToMany(mappedBy = "studentsCourses")
    List<Student> students;

}