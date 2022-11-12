package ua.spring.crud.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.spring.crud.models.Book;
import ua.spring.crud.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {

        return  jdbcTemplate.query("SELECT * FROM person",new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?",new Object[]{id},new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (full_name,dob) VALUES(?,?)",
                person.getFullName(),person.getDob());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET full_name=?,dob=? WHERE id=?",
                person.getFullName(),person.getDob(),id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person where id=?",id);
    }


    public Optional<Person> showUnique(String fullName, String dob, int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE full_name=? AND dob=? AND id!=?",new Object[]{fullName,dob,id},new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }


    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE  person_id=?",new Object[]{id},new BeanPropertyRowMapper<>(Book.class));
    }
}
