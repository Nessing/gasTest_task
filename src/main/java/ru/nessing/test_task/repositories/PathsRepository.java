package ru.nessing.test_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nessing.test_task.Entities.Path;

@Repository
public interface PathsRepository extends JpaRepository<Path, Long> {
}
