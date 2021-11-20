package org.acme.micros.webapp.repository;

import java.util.List;
import java.util.Set;

import org.acme.micros.webapp.domain.Person;
import org.acme.micros.webapp.repository.projection.PersonFiltered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByName(String name);

    @Query("SELECT p.id AS id, p.name AS name, p.birthDate AS birthDate, " +
               "p.dateCreated AS dateCreated, p.dateModified AS dateModified " +
               "FROM Person p " +
               "WHERE YEAR(p.birthDate) NOT IN (?1)")
    List<PersonFiltered> findByNotIn(Set<Integer> bannedYears);
}
