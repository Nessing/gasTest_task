package ru.nessing.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nessing.server.Entities.Path;

@Repository
public interface PathsRepository extends JpaRepository<Path, Long> {
}
