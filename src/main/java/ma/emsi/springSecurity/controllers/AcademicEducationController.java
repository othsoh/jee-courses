package ma.emsi.springSecurity.controllers;

import ma.emsi.springSecurity.entities.AcademicEducation;
import ma.emsi.springSecurity.entities.User;
import ma.emsi.springSecurity.repositories.AcademicEducationRepository;
import ma.emsi.springSecurity.repositories.EnrolledStudentRepository;
import ma.emsi.springSecurity.repositories.TeacherRepository;
import ma.emsi.springSecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AcademicEducationController {
    @Autowired
    AcademicEducationRepository academicEducationRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    private UserService userService;

    @Autowired
    EnrolledStudentRepository enrolledStudentRepository;

    @GetMapping(path= {"/AcademicEducation", })
    public String findAcademicEducation(Model model , Authentication authentication,
                                        @RequestParam(name="page", defaultValue = "0") int page,
                                        @RequestParam(name="taille", defaultValue = "6") int taille,
                                        @RequestParam(name="search",defaultValue = "") String search){
        Page<AcademicEducation> academicEducations ;
        User user = userService.getUserByEmail(authentication.getName());
        if (search.isEmpty()) {
            academicEducations = academicEducationRepository.findAll(PageRequest.of(page,taille));
        } else {
            academicEducations = academicEducationRepository.findByProgramName(search, PageRequest.of(page,taille));
        }
        int[] pages = new int[academicEducations.getTotalPages()];
        for(int i=0;i<pages.length;i++)
            pages[i]=i;
        model.addAttribute("listAcademicEducation", academicEducations.getContent());
        model.addAttribute("CurrentPage", page);
        model.addAttribute("size", taille);
        model.addAttribute("listPages", pages);
        model.addAttribute("search", search);
        model.addAttribute("Teachers", teacherRepository.findAll());

        return "AcademicEducation/index";
    }

    @GetMapping("/AcademicEducation/add")
    public String addAcademicEducation(Model model) {
        model.addAttribute("AcademicEducation", new AcademicEducation());
        model.addAttribute("EnrolledStudents", enrolledStudentRepository.findAll());
        model.addAttribute("Teachers", teacherRepository.findAll());
        return "AcademicEducation/addAcademicEducation";
    }

    @PostMapping("/AcademicEducation/save")
    public String saveAcademicEducation(@ModelAttribute AcademicEducation academicEducation) {
        academicEducationRepository.save(academicEducation);
        return "redirect:/AcademicEducation";
    }

    @GetMapping("/AcademicEducation/edit/{id}")
    public String editAcademicEducation(@PathVariable Long id, Model model) {
        AcademicEducation AcademicEducation = academicEducationRepository.findById(id).orElse(null);
        model.addAttribute("AcademicEducation", AcademicEducation);
        model.addAttribute("EnrolledStudents", enrolledStudentRepository.findAll());
        model.addAttribute("Teachers", teacherRepository.findAll());
        return "AcademicEducation/editAcademicEducation";
    }

    @PostMapping("/AcademicEducation/update/{id}")
    public String updateAcademicEducation(@PathVariable Long id, @ModelAttribute AcademicEducation AcademicEducation) {
        AcademicEducation.setEducationID(id);
        academicEducationRepository.save(AcademicEducation);

        return "redirect:/AcademicEducation";
    }

    @GetMapping("/AcademicEducation/delete/{id}")
    public String deleteAcademicEducation(@PathVariable Long id) {
        academicEducationRepository.deleteById(id);
        return "redirect:/AcademicEducation";
    }
}