package com.reactapi.reactapi.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
// import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactapi.reactapi.Exception.ResourseNotFoundException;
import com.reactapi.reactapi.Model.Course;
import com.reactapi.reactapi.Repository.CourseRepository;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    // get all Employees
    @GetMapping("/courses")
    private List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Add employee to DB
    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course Course) {
        return courseRepository.save(Course);

    }

    // Get Single/element by id
    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Employee not exist with id:" + id));
        return ResponseEntity.ok(course);
    }

    // update db
    @PostMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id,@RequestBody Course coursedetail) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Employee not exist with id:" + id));
        course.setTitle(coursedetail.getTitle());
        course.setDescription(coursedetail.getDescription());
        Course updatedCourse = courseRepository.save(course);
        return ResponseEntity.ok(updatedCourse);
    }

//Delete specific from DB
@DeleteMapping("/courses/{id}")
public ResponseEntity<Map<String,Boolean>> deleteCourse(@PathVariable Long id){
     Course course = courseRepository.findById(id).orElseThrow(() -> new 
             ResourseNotFoundException("Employee not exist with id:" + id));
     courseRepository.delete(course);
     Map<String,Boolean> response=new HashMap<>();
     response.put("Deleted", true);
     return ResponseEntity.ok(response);



}

}
