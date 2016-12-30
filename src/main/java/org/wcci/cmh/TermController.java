package org.wcci.cmh;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class TermController {

    @Resource
    private TermRepository myTermRepository;
    
    @Resource 
    private UserRepository myUserRepository;
    
    @Resource
    private TermStatusRepository termStatusRepository;

    @RequestMapping("/all")
    public String displayEntireListOfTerms(Model model) {

        Iterable<Term> terms = myTermRepository.findAll();
        model.addAttribute("terms", terms);
        return "term-list";
    }


    @RequestMapping("/term-single")
    public String displayASingleTerm(@RequestParam(value = "name", required = false) long id, Model model) {
        Term term = myTermRepository.findOne(id);
        model.addAttribute("selectedTerm", term);
        return "term-single";
    }

    @RequestMapping("/search")
    public String search(@RequestParam(value = "title") String title, Model model) {
        Iterable<Term> searchResults = myTermRepository.findByTitleIgnoreCaseLike("%" + title + "%");
        model.addAttribute("terms", searchResults);
        return "term-list";
    }

    @RequestMapping("/add")
    public String add(@RequestParam(value = "title") String title, Model model) {
        Term searchResults = myTermRepository.findByTitleIgnoreCase(title);
        if (searchResults == null) {
            Term term = new Term(title);
            myTermRepository.save(term);
            Iterable<User> allUsers = myUserRepository.findAll();
            if (allUsers != null && allUsers.iterator() != null && allUsers.iterator().hasNext()){
            	for(User user : allUsers) {
            		TermStatus termStatus = new TermStatus();
            		termStatus.setTerm(term);
            		termStatus.setUser(user);
            		termStatus.setDone(false);
            		user.getTermStatuses().add(termStatus);
            		myUserRepository.save(user);
            	}
            }
        }
        return "redirect:/all";
        //return displayEntireListOfTerms(model);
    }

    @RequestMapping("/remove")
    public String remove(@RequestParam(value = "title") String title, Model model) {
        Term term = myTermRepository.findByTitleIgnoreCase(title);
        myTermRepository.delete(term);
        return displayEntireListOfTerms(model);
    }


}