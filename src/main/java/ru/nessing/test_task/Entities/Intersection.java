package ru.nessing.test_task.Entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "intersections")
public class Intersection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinTable(name = "intersections_names",
            joinColumns = @JoinColumn(name = "intersection_id"),
            inverseJoinColumns = @JoinColumn(name = "name_id")
    )
    private List<Name> names;

    @OneToMany
    @JoinTable(name = "users_paths",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "path_id")
    )
    private List<Path> paths;
}
