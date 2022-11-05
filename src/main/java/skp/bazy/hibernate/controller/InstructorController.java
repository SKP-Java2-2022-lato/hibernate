package skp.bazy.hibernate.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skp.bazy.hibernate.exception.ResourceNotFoundException;
import skp.bazy.hibernate.model.Course;
import skp.bazy.hibernate.model.Instructor;
import skp.bazy.hibernate.model.InstructorDetail;
import skp.bazy.hibernate.repository.InstructorRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    InstructorRepository instructorRepository;

    @GetMapping("")
    public List<Instructor> getInstructors(){
        return instructorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable(value = "id") Long instructorId) throws ResourceNotFoundException {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(()-> new ResourceNotFoundException("Instructor not found :: "+ instructorId));

        return ResponseEntity.ok().body(instructor);
    }

    @GetMapping("/{id}/courses")
    public List<Course> getInstructorCourses(@PathVariable(value = "id") Long instructorId) throws ResourceNotFoundException {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(()-> new ResourceNotFoundException("Instructor not found :: "+ instructorId));

        return instructor.getCourses();
    }



    @PostMapping("")
    public Instructor createInstructor(@Valid @RequestBody Instructor instructor){
        return instructorRepository.save(instructor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable(value = "id") Long instructorId,
                                                       @Valid @RequestBody Instructor instructorDetails) throws ResourceNotFoundException {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(()-> new ResourceNotFoundException("Instructor not found :: "+ instructorId));

        instructor.setEmail(instructorDetails.getEmail());

        Instructor updated = instructorRepository.save(instructor);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/editDetails")
    public ResponseEntity<Instructor> updateInstructorDetail(@PathVariable(value = "id") Long instructorId,
                                                       @Valid @RequestBody InstructorDetail
                                                               instructorDetails) throws ResourceNotFoundException {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(()-> new ResourceNotFoundException("Instructor not found :: "+ instructorId));

       instructor.setInstructorDetail(instructorDetails);

        Instructor updated = instructorRepository.save(instructor);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteInstructor(@PathVariable(value = "id") Long instructorId) throws ResourceNotFoundException {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(()-> new ResourceNotFoundException("Instructor not found :: "+ instructorId));

        instructorRepository.delete(instructor);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
