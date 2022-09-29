package ru.nessing.test_task.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "paths")
public class Path {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path")
    private String path;
}
