package ua.spring.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.spring.crud.models.Book;
import ua.spring.crud.models.Person;
import ua.spring.crud.service.BooksService;
import ua.spring.crud.service.PeopleService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "books_per_page", required = false) Integer booksPerPage
            , @RequestParam(value = "sorted_by_year", required = false) boolean sorted) {


        if (page == null || booksPerPage == null)
            model.addAttribute("books", booksService.findAll(sorted));
        else
            model.addAttribute("books", booksService.findWithPagination(page, booksPerPage, sorted));

        return "books/index";
    }


    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.show(id));

        Optional<Person> bookOwner = Optional.ofNullable(booksService.getBookOwner(id));

        bookOwner.ifPresentOrElse((p) -> model.addAttribute("owner", p)
                , () -> model.addAttribute("people", peopleService.index()));

        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        booksService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {


        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search() {

        return "books/search";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", booksService.findByTitleStartingWith(query));
        return "books/search";
    }
}
