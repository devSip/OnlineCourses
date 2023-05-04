package lt.codeacademy.kursutinklalapis.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import lt.codeacademy.kursutinklalapis.entities.User;
import lt.codeacademy.kursutinklalapis.services.UserService;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<User> getAllStudents() {
		return userService.getAllStudents();
	}

	@GetMapping("/{id}")
	public User getStudentById(@PathVariable Long id) {
		return userService.getStudentById(id);
	}

	@PostMapping
	public ResponseEntity<User> createStudent(@RequestBody User student) throws URISyntaxException {
		userService.createStudent(student);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}/update")
	public ResponseEntity updateStudent(@PathVariable Long id, @RequestBody User student) {
		User currentStudent = userService.updateStudent(id, student);
		return ResponseEntity.ok(currentStudent);
	}

	@DeleteMapping("/{id}/delete")
	public ResponseEntity deleteStudent(@PathVariable Long id) {
		userService.deleteStudentById(id);
		return ResponseEntity.ok().build();
	}

}
