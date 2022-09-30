package ru.nessing.server.Entities;

import lombok.Data;
import ru.nessing.server.enums.States;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinTable(name = "intersections_results",
            joinColumns = @JoinColumn(name = "result_id"),
            inverseJoinColumns = @JoinColumn(name = "intersection_id")
    )
    private List<Intersection> intersectionList;

    @Column(name = "state")
    private States states;
}
