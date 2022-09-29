package ru.nessing.test_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nessing.test_task.Entities.Name;

@Repository
public interface NamesRepository extends JpaRepository<Name, Long> {
    Name findNameByFirstName(String firstName);
    Name findNameById(Long id);
}
