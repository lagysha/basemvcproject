package ua.spring.crud.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ua.spring.crud.models.Person;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Optional;

@Component
public class PersonDao {

    private final EntityManager entityManager;

    @Autowired
    public PersonDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Optional<Person> showUnique(String fullName, Date dob, int id) {
        return entityManager.createQuery("SELECT p FROM Person p WHERE p.fullName=:name AND p.dob=:dob AND p.id<>:id ",Person.class)
                .setParameter("name",fullName)
                .setParameter("dob",dob)
                .setParameter("id",id)
                .getResultList()
                .stream().findAny();
    }
}
