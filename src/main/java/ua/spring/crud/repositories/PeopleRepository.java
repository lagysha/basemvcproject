package ua.spring.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.spring.crud.models.Person;

import java.util.List;
import java.util.Optional;


@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
    Optional<Person> findByFullNameAndDobAndIdIsNot(String name, String dob, int id);
}
