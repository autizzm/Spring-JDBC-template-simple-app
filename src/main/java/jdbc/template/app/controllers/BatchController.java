package jdbc.template.app.controllers;

import jdbc.template.app.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-batch-update")
public class BatchController {

    private final PersonDao personDao;

    @Autowired
    public BatchController(PersonDao personDao) {
        this.personDao = personDao;
    }


    @GetMapping()
    public String index(Model model){
        model.addAttribute("peopleList", personDao.index());
        return "batch/index";
    }

    @GetMapping("/without")
    public String withoutBatch(){
        personDao.testMultipleUpdate();
        return "redirect:/people";
    }

    @GetMapping("/with")
    public String withBatch(){
        personDao.testBatchUpdate();
        return "redirect:/people";
    }

    @DeleteMapping("/delete-test-people")
    public String deleteTestPeople(){
        personDao.deleteTestPeople();
        return "redirect:/people";
    }
}
