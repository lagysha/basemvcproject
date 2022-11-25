package ua.spring.crud.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.spring.crud.models.Book;
import ua.spring.crud.models.Person;
import ua.spring.crud.repositories.PeopleRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> index() {

        return  peopleRepository.findAll();
    }

    public Person show(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional()
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional()
    public void update(int id, Person person) {
        System.out.println("3");
        person.setId(id);
        peopleRepository.save(person);
    }

    public List<Person> findAll(boolean sorted) {
        return peopleRepository.findAll(sorted ? Sort.by("fullName") : Sort.unsorted());
    }

    public List<Person> findWithPagination(int page, int peoplePerPage, boolean sorted) {
        return peopleRepository.findAll(PageRequest.of(page, peoplePerPage,
                sorted ? Sort.by("fullName") : Sort.unsorted())).getContent();
    }

    @Transactional()
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }


    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if(person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            person.get().getBooks().forEach( book -> {
                long diff = Math.abs(book.getTaken_at().getTime() - new Date().getTime());

                if(diff > 864_000_000)
                    book.setExpired(true);
            });
            return person.get().getBooks();
        }
        else {
            return Collections.emptyList();
        }
    }
}
