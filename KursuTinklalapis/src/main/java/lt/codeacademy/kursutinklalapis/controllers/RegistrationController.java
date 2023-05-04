package lt.codeacademy.kursutinklalapis.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import lt.codeacademy.kursutinklalapis.entities.Registration;
import lt.codeacademy.kursutinklalapis.entities.RegistrationRequestDto;
import lt.codeacademy.kursutinklalapis.services.RegistrationService;

@RestController
@RequestMapping("/api/registrations")

public class RegistrationController {
	@Autowired
	private RegistrationService registrationService;

	public RegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@GetMapping
	public List<Registration> getAllRegistrations() {
		return registrationService.getAllRegistrations();
	}

	@GetMapping("/{id}")
	public Registration getRegistrationById(@PathVariable Long id) {
		return registrationService.getRegistrationById(id);
	}

	@PostMapping
	public ResponseEntity createRegistration(@RequestBody RegistrationRequestDto requestDto) throws URISyntaxException {
		registrationService.createRegistration(requestDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/{id}/update")
	public ResponseEntity updateRegistration(@PathVariable Long id, @RequestBody Registration registration) {
		Registration currentRegistration = registrationService.updateRegistration(id, registration);
		return ResponseEntity.ok(currentRegistration);
	}

	@DeleteMapping("/{id}/delete")
	public ResponseEntity deleteRegistration(@PathVariable Long id) {
		registrationService.deleteRegistrationById(id);
		return ResponseEntity.ok().build();
	}
}