package lt.codeacademy.kursutinklalapis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lt.codeacademy.kursutinklalapis.entities.Course;
import lt.codeacademy.kursutinklalapis.entities.Registration;
import lt.codeacademy.kursutinklalapis.entities.RegistrationRequestDto;
import lt.codeacademy.kursutinklalapis.entities.User;
import lt.codeacademy.kursutinklalapis.repositories.CourseRepository;
import lt.codeacademy.kursutinklalapis.repositories.RegistrationRepository;
import lt.codeacademy.kursutinklalapis.repositories.UserRepository;

@Service
@Transactional
public class RegistrationService {

	@Autowired
	private RegistrationRepository regRep;

	@Autowired
	private CourseRepository courseRep;

	@Autowired
	UserRepository userRep;

	public List<Registration> getAllRegistrations() {
		return regRep.findAll();
	}

	public Registration getRegistrationById(Long id) {
		return regRep.findById(id).orElseThrow(() -> new EntityNotFoundException("Registration not found"));
	}

	public Registration createRegistration(@RequestBody RegistrationRequestDto requestDto) {
		User user = userRep.findById(requestDto.getUserId())
				.orElseThrow(() -> new EntityNotFoundException("Student not found"));
		Course course = courseRep.findById(requestDto.getCourseId())
				.orElseThrow(() -> new EntityNotFoundException("Course not found"));
		if (user.getRegistrations().stream().anyMatch(registration -> registration.getCourse().equals(course))) {
			throw new IllegalStateException("Student is already enrolled in the course");
		}
		Registration registration = new Registration();
		registration.setUser(user);
		registration.setCourse(course);
		user.addRegistration(registration);
		return regRep.save(registration);
	}

	public Registration updateRegistration(Long id, Registration regDetails) {
		Registration registration = regRep.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Registration not found"));
		registration.setUser(regDetails.getUser());
		registration.setCourse(regDetails.getCourse());

		return regRep.save(registration);
	}

	public void deleteRegistrationById(Long id) {
		Registration registration = regRep.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Registration not found"));

		registration.getUser().removeRegistration(registration);
//		registration.getCourse().removeRegistration(registration);

		regRep.deleteById(id);
	}

}