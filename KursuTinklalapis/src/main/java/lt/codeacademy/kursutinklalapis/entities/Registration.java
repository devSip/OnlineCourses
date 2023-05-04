package lt.codeacademy.kursutinklalapis.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registrations")
public class Registration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "course_id")
	@JsonIgnoreProperties("registrations")
	private Course course;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties("registrations")
	private User user;

	//Added for testing purposes
	public Registration(RegistrationRequestDto regDto, User user, Course course) {
	    this.user = user;
	    this.course = course;
	}
}
