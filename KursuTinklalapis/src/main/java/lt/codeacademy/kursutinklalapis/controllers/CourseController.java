package lt.codeacademy.kursutinklalapis.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.codeacademy.kursutinklalapis.entities.Course;
import lt.codeacademy.kursutinklalapis.services.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {
	@Autowired
	private CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping
	public List<Course> getAllCourses() {
		return courseService.getAllCourses();
	}

	@GetMapping("/{id}")
	public Course getCourseById(@PathVariable Long id) {
		return courseService.getCourseById(id);
	}

	@PostMapping
	public ResponseEntity createCourse(@RequestBody Course course) throws URISyntaxException {
		Course newCourse = courseService.saveCourse(course);
		return ResponseEntity.created(new URI("/courses/" + newCourse.getId())).body(newCourse);
	}

	@PutMapping("/{id}/update")
	public ResponseEntity updateCourse(@PathVariable Long id, @RequestBody Course course) {
		Course currentCourse = courseService.updateCourse(id, course);
		return ResponseEntity.ok(currentCourse);
	}

	@DeleteMapping("/{id}/delete")
	public ResponseEntity deleteCourse(@PathVariable Long id) {
		courseService.deleteCourseById(id);
		return ResponseEntity.ok().build();
	}
}