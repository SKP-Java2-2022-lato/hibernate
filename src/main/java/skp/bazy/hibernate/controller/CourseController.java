package skp.bazy.hibernate.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skp.bazy.hibernate.exception.ResourceNotFoundException;
import skp.bazy.hibernate.model.Course;
import skp.bazy.hibernate.model.Instructor;
import skp.bazy.hibernate.repository.CourseRepository;
import skp.bazy.hibernate.repository.InstructorRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @GetMapping("")
    public List<Course> getCourse(){
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return ResponseEntity.ok(course);
    }

    @PostMapping("/{instructorId}")
    public Course createCourse(@PathVariable(value = "instructorId") Long instructorId, @Valid @RequestBody Course course) throws ResourceNotFoundException {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(()-> new ResourceNotFoundException("Instructor not found"));

        course.setInstructor(instructor);
        return courseRepository.save(course);
    }

    @PutMapping("{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable(value = "id") Long courseId, @Valid @RequestBody Course courseDetails ) throws ResourceNotFoundException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        course.setTitle(courseDetails.getTitle());
        Course c = courseRepository.save(course);
        return ResponseEntity.ok(c);

    }

    @PutMapping("{id}/{instructorId}")
    public ResponseEntity<Course> updateCourseInstructor
            (@PathVariable(value = "id") Long courseId,@PathVariable(value = "instructorId") Long instructorId,
             @Valid @RequestBody Course courseDetails ) throws ResourceNotFoundException {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(()-> new ResourceNotFoundException("Instructor not found"));

        if(courseDetails.getTitle() != null){
            course.setTitle(courseDetails.getTitle());
        }
        course.setInstructor(instructor);
        return ResponseEntity.ok(courseRepository.save(course));

    }

    @DeleteMapping("{id}")
    public Map<String, Boolean> deleteCourse(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        course.setInstructor(null);
        courseRepository.save(course);

        courseRepository.delete(course);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
