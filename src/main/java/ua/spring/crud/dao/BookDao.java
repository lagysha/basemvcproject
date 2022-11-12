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
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?",new Object[]{id},new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book",new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(title,author,age) VALUES(?,?,?)"
                ,book.getTitle(),book.getAuthor(),book.getAge());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET title=?,author=?,age=? WHERE id=?",
                book.getTitle(),book.getAuthor(),book.getAge(),id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book where id=?",id);
    }


    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT person.* FROM book JOIN person ON " +
                        "Book.person_id = Person.id WHERE Book.id = ?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE id=?", id);
    }

    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", selectedPerson.getId(), id);
    }
}
