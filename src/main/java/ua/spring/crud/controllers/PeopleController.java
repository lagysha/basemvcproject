package ua.spring.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.spring.crud.models.Person;
import ua.spring.crud.service.PeopleService;
import ua.spring.crud.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonValidator personValidator;
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PersonValidator personValidator, PeopleService peopleService) {
        this.personValidator = personValidator;
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage
            , @RequestParam(value = "sorted_by_fullName", required = false) boolean sorted) {


        if (page == null || peoplePerPage == null)
            model.addAttribute("people", peopleService.findAll(sorted));
        else
            model.addAttribute("people", peopleService.findWithPagination(page, peoplePerPage, sorted));

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

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public  String edit(Model model,@PathVariable("id") int id){
        model.addAttribute("person",peopleService.show(id));
        return "people/edit";
    }





    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        //Get one person by id from DAO show in view
        model.addAttribute("person", peopleService.show(id));
        model.addAttribute("books",peopleService.getBooksByPersonId(id));
        return "people/show";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){

        personValidator.validate(person,bindingResult);

        if(bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }

}
