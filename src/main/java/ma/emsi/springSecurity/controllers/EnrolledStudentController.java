package ma.emsi.springSecurity.controllers;

import ma.emsi.springSecurity.entities.EnrolledStudent;
import ma.emsi.springSecurity.repositories.EnrolledStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller public class EnrolledStudentController {
    @Autowired
    EnrolledStudentRepository enrolledStudentRepository;

    @GetMapping(path= {"/EnrolledStudents", })
    public String findEnrolledStudents(Model model ,
                        @RequestParam(name="page", defaultValue = "0") int page,
                        @RequestParam(name="taille", defaultValue = "6") int taille,
                        @RequestParam(name="search",defaultValue = "") String search){
        Page<EnrolledStudent> enrolledStudents =  enrolledStudentRepository.findByFirstNameContains(search,PageRequest.of(page,taille));


        int[] pages = new int[enrolledStudents.getTotalPages()];
        for(int i=0;i<pages.length;i++)
            pages[i]=i;
        model.addAttribute("listEnrolledStudents", enrolledStudents.getContent());
        model.addAttribute("CurrentPage", page);
        model.addAttribute("size", taille);
        model.addAttribute("listPages", pages);
        model.addAttribute("search", search);



        return "EnrolledStudents/index";
    }
    @GetMapping(path= {"/", "", "/index" })
    public String Home(){
        return "Home";
    }

    @GetMapping("/EnrolledStudents/add")
    public String addEnrolledStudent(Model model) {
        model.addAttribute("EnrolledStudent", new EnrolledStudent());
        return "EnrolledStudents/addEnrolledStudent";
    }

    @PostMapping("/EnrolledStudents/save")
    public String saveEnrolledStudent(@ModelAttribute EnrolledStudent EnrolledStudent) {
        enrolledStudentRepository.save(EnrolledStudent);
        return "redirect:/EnrolledStudents";
    }

    @GetMapping("/EnrolledStudents/edit/{id}")
    public String editEnrolledStudent(@PathVariable int id, Model model) {
        EnrolledStudent enrolledStudent = enrolledStudentRepository.findById(id).orElse(null);
        model.addAttribute("EnrolledStudent", enrolledStudent);
        return "EnrolledStudents/editEnrolledStudent";
    }

    @PostMapping("/EnrolledStudents/update/{id}")
    public String updateEnrolledStudent(@PathVariable Long id, @ModelAttribute EnrolledStudent enrolledStudent) {
        enrolledStudent.setStudentID(id);
        enrolledStudentRepository.save(enrolledStudent);
        return "redirect:/EnrolledStudents";
    }

    @GetMapping("/EnrolledStudents/delete/{id}")
    public String deleteEnrolledStudent(@PathVariable int id) {
        enrolledStudentRepository.deleteById(id);
        return "redirect:/EnrolledStudents";
    }
}
