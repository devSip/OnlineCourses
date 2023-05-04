package lt.codeacademy.kursutinklalapis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import lt.codeacademy.kursutinklalapis.entities.Course;
import lt.codeacademy.kursutinklalapis.entities.Professor;
import lt.codeacademy.kursutinklalapis.entities.Role;
import lt.codeacademy.kursutinklalapis.entities.User;
import lt.codeacademy.kursutinklalapis.repositories.CourseRepository;
import lt.codeacademy.kursutinklalapis.repositories.ProfessorRepository;
import lt.codeacademy.kursutinklalapis.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Observer {

	@Autowired
	UserRepository userRep;

	@Autowired
	CourseRepository courseRep;

	@Autowired
	ProfessorRepository professorRep;

	@EventListener
	public void seed(ContextRefreshedEvent event) {
		if (userRep.findAll().isEmpty())
			seedUserDummyData();
		if (professorRep.findAll().isEmpty())
			seedProfessorDummyData();
		if (courseRep.findAll().isEmpty())
			seedCoursesDummyData();

	}

	/**
	 * This method seeds the user repository with dummy student data. It creates a
	 * list of User objects, with each object representing a student and containing
	 * their first name, last name, email address, hashed password, and role (which
	 * is set to ADMIN, PROFESSOR or STUDENT). The list of User objects is then
	 * saved to the user repository using the saveAll method. This method assumes
	 * that the user repository has already been initialized and is available for
	 * use.
	 */
	private void seedUserDummyData() {

		PasswordEncoder encoder = new BCryptPasswordEncoder();

		List<User> users = List.of(
				new User("Adminas", "Adminauskas", "admin@admin.lt", encoder.encode("admin"), Role.ADMIN),
				new User("Petronijus", "Petrauskas", "professor@professor.lt", encoder.encode("professor"),
						Role.PROFESSOR),
				new User("Jonas", "Petraitis", "jonas@mail.com", encoder.encode("12345678"), Role.STUDENT),
				new User("Petras", "Antanaitis", "petras@mail.com", encoder.encode("12345678"), Role.STUDENT),
				new User("Antanas", "Jonaitis", "antanas@mail.com", encoder.encode("12345678"), Role.STUDENT),
				new User("Mokytas", "Mokinys", "moksliukas@mail.lt", encoder.encode("12345678"), Role.STUDENT));

		userRep.saveAll(users);
	}

	/**
	 * This method is a private Java method that seeds the database with dummy data
	 * for the Professor entity. It creates a list of Professor objects with
	 * predefined names and email addresses, and then saves them to the database
	 * using the Professor repository's saveAll method. Note that this method
	 * assumes the existence of a professorRep object, which is an instance of a
	 * Professor repository that provides access to the database. The purpose of
	 * this method is to populate the database with some initial data, which can be
	 * useful for testing and development purposes.
	 */
	private void seedProfessorDummyData() {
		List<Professor> professors = List.of(new Professor("Pitagoras", "Pitagoras@mail.com"),
				new Professor("Rovanas Atkinsonas", "Bynas@mail.com"),
				new Professor("Albertas Einsteinas", "Fizika@mail.com"),
				new Professor("Herodotas", "Istorija@mail.com"),
				new Professor("Algimantas Cekuolis", "Cekuolis@mail.com"),
				new Professor("Kristupas Kolumbas", "Amerika@mail.com"));
		professorRep.saveAll(professors);
	}

	/**
	 * This method is a private Java method that seeds the database with dummy data
	 * for the Course entity. It creates a list of Course objects with predefined
	 * names, subjects, and professor names, and then saves them to the database
	 * using the Course repository's saveAll method. Note that this method assumes
	 * the existence of a courseRep object, which is an instance of a Course
	 * repository that provides access to the database. The purpose of this method
	 * is to populate the database with some initial data, which can be useful for
	 * testing and development purposes.
	 */
	private void seedCoursesDummyData() {

		List<Course> courses = List.of(new Course("Tikslieji mokslai", "Matematika", "Pitagoras"),
				new Course("Tikslieji mokslai", "Fizika", "Albertas Einsteinas"),
				new Course("Tikslieji mokslai", "Chemija", "Dimitrijus Mendelejevas"),
				new Course("Tikslieji mokslai", "Informacines technologijos", "Vaidas Cesonis"),
				new Course("Socialiniai mokslai", "Ekonomika", "Nerijus Maciulis"),
				new Course("Socialiniai mokslai", "Teise", "Vilija Venslovaite"),
				new Course("Socialiniai mokslai", "Psichologija", "Rasa Barkauskiene"),
				new Course("Socialiniai mokslai", "Istorija", "Herodotas"),
				new Course("Humanitariniai mokslai", "Menotyra", "Rovanas Atkinsonas"),
				new Course("Humanitariniai mokslai", "Filosofija", "Leonidas Donskis"),
				new Course("Gamtos mokslai", "Geografija", "Kristupas Kolumbas"),
				new Course("Gamtos mokslai", "Biologija", "Carlzas Darvinas"));
		courseRep.saveAll(courses);
	}

}