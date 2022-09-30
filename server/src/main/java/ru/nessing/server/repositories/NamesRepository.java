package ru.nessing.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nessing.server.Entities.Name;

@Repository
public interface NamesRepository extends JpaRepository<Name, Long> {
    Name findNameByFirstName(String firstName);
    Name findNameById(Long id);
}
