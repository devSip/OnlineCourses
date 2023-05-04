package lt.codeacademy.kursutinklalapis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lt.codeacademy.kursutinklalapis.entities.Role;
import lt.codeacademy.kursutinklalapis.entities.User;
import lt.codeacademy.kursutinklalapis.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRep;

	public List<User> getAllStudents() {
		return userRep.findByRole(Role.STUDENT);
	}

	public User getStudentById(Long id) {
		return userRep.findByIdAndRole(id, Role.STUDENT)
				.orElseThrow(() -> new EntityNotFoundException("Student with ID " + id + " not found"));
	}

	public User createStudent(User user) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole(Role.STUDENT);

		return userRep.save(user);

	}

	public User updateStudent(Long id, User student) {
		User existingStudent = userRep.findByIdAndRole(id, Role.STUDENT)
				.orElseThrow(() -> new EntityNotFoundException("Student with id " + id + " not found"));

		existingStudent.setFirstname(student.getFirstname());
		existingStudent.setLastname(student.getLastname());
		existingStudent.setEmail(student.getEmail());
		existingStudent.setRegistrations(student.getRegistrations());

		return userRep.save(existingStudent);
	}

	public void deleteStudentById(Long id) {
		User student = userRep.findByIdAndRole(id, Role.STUDENT)
				.orElseThrow(() -> new EntityNotFoundException("Student with id " + id + " not found"));

		userRep.delete(student);
	}
}
