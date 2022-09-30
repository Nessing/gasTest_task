package ru.nessing.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nessing.server.Entities.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    Result findAllById(Long id);
}
