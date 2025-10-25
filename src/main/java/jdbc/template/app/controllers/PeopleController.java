package jdbc.template.app.controllers;


import jakarta.validation.Valid;
import jdbc.template.app.dao.PersonDao;
import jdbc.template.app.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PersonDao personDao;

    @Autowired
    public PeopleController(PersonDao personDao){
        this.personDao = personDao;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("peopleList", personDao.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id, Model model){
        System.out.println("Searching for person with id=" + id);
        Person person = personDao.findPerson(id);
        System.out.println("found person with name="+ person.getName());
        model.addAttribute("person", person);
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "people/new"; //если есть ошибки - сразу возвращаем пользователя обратно на формуpersonDao.save(person);

        personDao.save(person);
        return "redirect:/people";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id")int id) {
        model.addAttribute("person", personDao.findPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "people/edit";

        personDao.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id")int id){
        personDao.delete(id);
        return "redirect:/people";
    }
}
