package ma.emsi.springSecurity.controllers;

import jakarta.validation.Valid;
import ma.emsi.springSecurity.entities.AcademicEducation;
import ma.emsi.springSecurity.repositories.AcademicEducationRepository;
import ma.emsi.springSecurity.repositories.TeacherRepository;
import ma.emsi.springSecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AcademicEducationRestController {

    @Autowired
    AcademicEducationRepository academicEducationRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/AcademicEducation")
    public Map<String, Object> findAcademicEducation(@RequestParam(name="page", defaultValue = "0") int page,
                                                     @RequestParam(name="size", defaultValue = "6") int size,
                                                     @RequestParam(name="search",defaultValue = "") String search){
        Page<AcademicEducation> academicEducations;
        if (search.isEmpty()) {
            academicEducations = academicEducationRepository.findAll(PageRequest.of(page,size));
        } else {
            academicEducations = academicEducationRepository.findByProgramName(search, PageRequest.of(page,size));
        }
        Map<String, Object> response = new HashMap<>();
        response.put("listAcademicEducation", academicEducations.getContent());
        response.put("CurrentPage", page);
        response.put("size", size);
        response.put("listPages", academicEducations.getTotalPages());
        response.put("search", search);
        response.put("Teachers", teacherRepository.findAll());
        return response;
    }
    @GetMapping("/AcademicEducation/{id}")
    public ResponseEntity<AcademicEducation> getAcademicEducationById(@PathVariable Long id) {
        Optional<AcademicEducation> academicEducation = academicEducationRepository.findById(id);
        return academicEducation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/AcademicEducation",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademicEducation> addAcademicEducation(@Valid @RequestBody AcademicEducation academicEducation) {
        AcademicEducation savedAcademicEducation = academicEducationRepository.save(academicEducation);
        return ResponseEntity.ok(savedAcademicEducation);
    }

    @PutMapping("/AcademicEducation/{id}")
    public AcademicEducation updateAcademicEducation(@PathVariable Long id, @RequestBody AcademicEducation academicEducation) {
        academicEducation.setEducationID(id);
        return academicEducationRepository.save(academicEducation);
    }

    @DeleteMapping("/AcademicEducation/{id}")
    public void deleteAcademicEducation(@PathVariable Long id) {
        academicEducationRepository.deleteById(id);
    }
}