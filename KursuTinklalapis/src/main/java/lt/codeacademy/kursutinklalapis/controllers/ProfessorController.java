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

import lt.codeacademy.kursutinklalapis.entities.Professor;
import lt.codeacademy.kursutinklalapis.services.ProfessorService;

@RestController
@RequestMapping("/professors")
public class ProfessorController {
	@Autowired
	private ProfessorService professorService;

	public ProfessorController(ProfessorService professorService) {
		this.professorService = professorService;
	}

	@GetMapping
	public List<Professor> getAllProfessors() {
		return professorService.getAllProfessors();
	}

	@GetMapping("/{id}")
	public Professor getProfessorById(@PathVariable Long id) {
		return professorService.getProfessorById(id);
	}

	@PostMapping
	public ResponseEntity createProfessor(@RequestBody Professor professor) throws URISyntaxException {
		Professor newProfessor = professorService.createProfessor(professor);
		return ResponseEntity.created(new URI("/professors/" + newProfessor.getId())).body(newProfessor);
	}

	@PutMapping("/{id}/update")
	public ResponseEntity updateProfessor(@PathVariable Long id, @RequestBody Professor professor) {
		Professor currentProfessor = professorService.updateProfessor(id, professor);
		return ResponseEntity.ok(currentProfessor);
	}

	@DeleteMapping("/{id}/delete")
	public ResponseEntity deleteProfessor(@PathVariable Long id) {
		professorService.deleteProfessor(id);
		return ResponseEntity.ok().build();
	}
}
