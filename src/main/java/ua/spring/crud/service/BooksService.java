package ua.spring.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.spring.crud.models.Book;
import ua.spring.crud.models.Person;
import ua.spring.crud.repositories.BooksRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public Book show(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional()
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        Book bookToUpdate = booksRepository.findById(id).get();


        book.setId(id);
        book.setOwner(bookToUpdate.getOwner());
        book.setTaken_at(bookToUpdate.getTaken_at());

        System.out.println(book.getOwner());
        System.out.println(book.getTaken_at());

        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }


    public Person getBookOwner(int id) {
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    public List<Book> findByTitleStartingWith(String startingLetter) {
        return booksRepository.findByTitleStartingWith(startingLetter);
    }

    public List<Book> findAll(boolean sorted) {
        return booksRepository.findAll(sorted ? Sort.by("age") : Sort.unsorted());
    }

    public List<Book> findWithPagination(int page, int booksPerPage, boolean sorted) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage,
                sorted ? Sort.by("age") : Sort.unsorted())).getContent();
    }

    @Transactional
    public void release(int id) {
        Optional<Book> book = booksRepository.findById(id);
        book.ifPresent(value -> {
            value.setOwner(null);
            value.setTaken_at(null);
        });
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        Optional<Book> book = booksRepository.findById(id);
        book.ifPresent(value -> value.setOwner(selectedPerson));
        book.ifPresent(value -> value.setTaken_at(new Date()));
    }
}
