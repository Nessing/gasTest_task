package ru.nessing.test_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nessing.test_task.Entities.Intersection;

@Repository
public interface IntersectionRepository extends JpaRepository<Intersection, Long> {
    Intersection findIntersectionById(Long id);

}
