package lt.codeacademy.kursutinklalapis.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.kursutinklalapis.security.token.Token;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String subject;
	private String description;
	private String professorName;

	@OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Registration> registrations = new ArrayList<>();

	public Course(String subject, String description, String professorName) {
		super();
		this.subject = subject;
		this.description = description;
		this.professorName = professorName;
	}

	public Course(Long id, String subject, String description, String professorName) {
		super();
		this.id = id;
		this.subject = subject;
		this.description = description;
		this.professorName = professorName;
	}
}
