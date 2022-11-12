package ua.spring.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.spring.crud.dao.BookDao;
import ua.spring.crud.dao.PersonDao;
import ua.spring.crud.models.Person;
import ua.spring.crud.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDao personDao;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDao personDao, PersonValidator personValidator, BookDao bookDao) {
        this.personDao = personDao;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        //Get all people from DAO and show in view
        model.addAttribute("people", personDao.index());
        return "people/index";
    }



    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){

        personValidator.validate(person,bindingResult);

        if(bindingResult.hasErrors())
            return "people/new";

        personDao.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public  String edit(Model model,@PathVariable("id") int id){
        model.addAttribute("person",personDao.show(id));
        return "people/edit";
    }





    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        //Get one person by id from DAO show in view
        model.addAttribute("person", personDao.show(id));
        model.addAttribute("books",personDao.getBooksByPersonId(id));
        return "people/show";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){

        personValidator.validate(person,bindingResult);

        if(bindingResult.hasErrors())
            return "people/edit";

        personDao.update(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDao.delete(id);
        return "redirect:/people";
    }

}
