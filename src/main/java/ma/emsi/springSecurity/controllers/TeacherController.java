package ma.emsi.springSecurity.controllers;



import jakarta.validation.Valid;
import ma.emsi.springSecurity.entities.Teacher;
import ma.emsi.springSecurity.repositories.EnrolledStudentRepository;
import ma.emsi.springSecurity.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller public class TeacherController {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    EnrolledStudentRepository enrolledStudentRepository;
    @Autowired
    public TeacherController(TeacherRepository teacherRepository,  EnrolledStudentRepository enrolledStudentRepository) {
        this.teacherRepository = teacherRepository;
        this.enrolledStudentRepository = enrolledStudentRepository;
//        this.projetRepository = projetRepository;
    }

    @GetMapping(path= {"/Teachers", })
    public String findTeachers(Model model ,
                        @RequestParam(name="page", defaultValue = "0") int page,
                        @RequestParam(name="taille", defaultValue = "6") int taille,
                        @RequestParam(name="search",defaultValue = "") String search){
        Page<Teacher> teachers =  teacherRepository.findAll(PageRequest.of(page,taille));


        int[] pages = new int[teachers.getTotalPages()];
        for(int i=0;i<pages.length;i++)
            pages[i]=i;
        model.addAttribute("listTeachers", teachers.getContent());
        model.addAttribute("CurrentPage", page);
        model.addAttribute("size", taille);
        model.addAttribute("listPages", pages);
        model.addAttribute("search", search);



        return "teachers/index";
    }
    @PostMapping("teacher/Add")
    public String Add(Model n , @Valid Teacher post , BindingResult bindingResult ){
        if(bindingResult.hasErrors())
            return "addTeacher";
        teacherRepository.save(post);
        return "redirect:/";
    }

    @GetMapping("teacher/addTeacher")
    public String AddPost(Model m){
        return "teachers/addTeacher";
    }

    @GetMapping("/Teachers/add")
    public String addTeacher(Model model) {
        model.addAttribute("Teacher", new Teacher());
        model.addAttribute("EnrolledStudents", enrolledStudentRepository.findAll());
        return "teachers/addTeacher";
    }

    @PostMapping("/Teachers/save")
    public String saveTeacher(@ModelAttribute Teacher teacher) {
        teacherRepository.save(teacher);
        return "redirect:/Teachers";
    }

    @GetMapping("/Teachers/edit/{id}")
    public String editTeacher(@PathVariable int id, Model model) {
        Teacher Teacher = teacherRepository.findById(id).orElse(null);
        model.addAttribute("Teacher", Teacher);
        model.addAttribute("EnrolledStudents", enrolledStudentRepository.findAll());

        return "teachers/editTeacher";
    }

    @PostMapping("/Teachers/update/{id}")
    public String updateTeacher(@PathVariable Long id, @ModelAttribute Teacher Teacher) {
        Teacher.setTeacherID(id);
        teacherRepository.save(Teacher);
        return "redirect:/Teachers";
    }

    @GetMapping("/Teachers/delete/{id}")
    public String deleteTeacher(@PathVariable int id) {
        teacherRepository.deleteById(id);
        return "redirect:/Teachers";
    }
}
