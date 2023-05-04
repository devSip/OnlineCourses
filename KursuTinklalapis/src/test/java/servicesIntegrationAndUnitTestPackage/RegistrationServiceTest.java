package servicesIntegrationAndUnitTestPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lt.codeacademy.kursutinklalapis.KursuTinklalapisApplication;
import lt.codeacademy.kursutinklalapis.entities.Course;
import lt.codeacademy.kursutinklalapis.entities.Registration;
import lt.codeacademy.kursutinklalapis.entities.RegistrationRequestDto;
import lt.codeacademy.kursutinklalapis.entities.Role;
import lt.codeacademy.kursutinklalapis.entities.User;
import lt.codeacademy.kursutinklalapis.repositories.CourseRepository;
import lt.codeacademy.kursutinklalapis.repositories.RegistrationRepository;
import lt.codeacademy.kursutinklalapis.repositories.UserRepository;
import lt.codeacademy.kursutinklalapis.services.RegistrationService;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = KursuTinklalapisApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(properties = {
	    "spring.datasource.url=jdbc:h2:mem:testdb" + "${random.uuid}?mode=MySQL",
	    "spring.datasource.driver-class-name=org.h2.Driver",
	    "spring.datasource.username=three",
	    "spring.datasource.password=",
	    "spring.jpa.hibernate.ddl-auto=create-drop"
	})
class RegistrationServiceTest {

	@Autowired
	private UserRepository uRep;
	
	@Autowired
	private CourseRepository cRep;
	
	@Autowired
	private RegistrationService regServ;
	
	@Autowired
	private RegistrationRepository regRep;

	RegistrationRequestDto regDto;
	
	@Test
	@Order(1)
	void createRegistrationsTest() {
		User user1 = new User("Studentas1", "StudPavarde1", "studentas1@mail.com", "123", Role.STUDENT);
		User user2 = new User("Studentas2", "StudPavarde2", "studentas2@mail.com", "456", Role.STUDENT);
		User user3 = new User("Studentas3", "StudPavarde3", "studentas3@mail.com", "789", Role.STUDENT);
		uRep.save(user1);
		uRep.save(user2);
		uRep.save(user3);
		
		Course course1 = new Course("Kursas1", "Mokslas1", "Profesorius1");
		Course course2 = new Course("Kursas2", "Mokslas2", "Profesorius2");
		Course course3 = new Course("Kursas3", "Mokslas3", "Profesorius3");
		cRep.save(course1);
		cRep.save(course2);
		cRep.save(course3);
		
		RegistrationRequestDto reg1 = new RegistrationRequestDto(user1.getId(), course1.getId());
		RegistrationRequestDto reg2 = new RegistrationRequestDto(user2.getId(), course2.getId());
		RegistrationRequestDto reg3 = new RegistrationRequestDto(user3.getId(), course3.getId());
		
		regServ.createRegistration(reg1);
		regServ.createRegistration(reg2);
		regServ.createRegistration(reg3);
		
		assertNotNull(regServ.getAllRegistrations());
		assertNotNull(regServ.getRegistrationById(1L));
		assertNotNull(regServ.getRegistrationById(2L));
		assertNotNull(regServ.getRegistrationById(3L));
		assertEquals(3, regServ.getAllRegistrations().size());
	}
	@Test
	@Order(2)
	void checkRegistrationDataTest() {
		assertEquals("Studentas1", regServ.getRegistrationById(1L).getUser().getFirstname());
		assertEquals("StudPavarde1", regServ.getRegistrationById(1L).getUser().getLastname());
		assertEquals("studentas1@mail.com", regServ.getRegistrationById(1L).getUser().getEmail());
		assertEquals("Studentas2", regServ.getRegistrationById(2L).getUser().getFirstname());
		assertEquals("StudPavarde2", regServ.getRegistrationById(2L).getUser().getLastname());
		assertEquals("studentas2@mail.com", regServ.getRegistrationById(2L).getUser().getEmail());
		assertEquals("Studentas3", regServ.getRegistrationById(3L).getUser().getFirstname());
		assertEquals("StudPavarde3", regServ.getRegistrationById(3L).getUser().getLastname());
		assertEquals("studentas3@mail.com", regServ.getRegistrationById(3L).getUser().getEmail());
		
		assertEquals("Kursas1", regServ.getRegistrationById(1L).getCourse().getSubject());
		assertEquals("Mokslas1", regServ.getRegistrationById(1L).getCourse().getDescription());
		assertEquals("Profesorius1", regServ.getRegistrationById(1L).getCourse().getProfessorName());
		assertEquals("Kursas2", regServ.getRegistrationById(2L).getCourse().getSubject());
		assertEquals("Mokslas2", regServ.getRegistrationById(2L).getCourse().getDescription());
		assertEquals("Profesorius2", regServ.getRegistrationById(2L).getCourse().getProfessorName());
		assertEquals("Kursas3", regServ.getRegistrationById(3L).getCourse().getSubject());
		assertEquals("Mokslas3", regServ.getRegistrationById(3L).getCourse().getDescription());
		assertEquals("Profesorius3", regServ.getRegistrationById(3L).getCourse().getProfessorName());
	}
	@Test
	@Order(3)
	void updateRegistrationsTest() {
				
		User upStud = new User("NaujasStud", "NaujaPav", "naujstud@mail.com", "123", Role.STUDENT);
		Course upCor = new Course("NaujasKur", "NuajasMoksl", "NaujasProf");
		
		cRep.save(upCor);
		uRep.save(upStud);
		
		Registration reg = regServ.getRegistrationById(1L);
	    reg.setCourse(upCor);
	    reg.setUser(upStud);
	    
	    regServ.updateRegistration(1L, reg);

		assertEquals("NaujasStud", regServ.getRegistrationById(1L).getUser().getFirstname());
		assertEquals("NaujaPav", regServ.getRegistrationById(1L).getUser().getLastname());
		assertEquals("naujstud@mail.com", regServ.getRegistrationById(1L).getUser().getEmail());
		assertEquals("NaujasKur", regServ.getRegistrationById(1L).getCourse().getSubject());
		assertEquals("NuajasMoksl", regServ.getRegistrationById(1L).getCourse().getDescription());
		assertEquals("NaujasProf", regServ.getRegistrationById(1L).getCourse().getProfessorName());
	}
	
	@Test
	@Order(4)
	void deleteRegistrationtest() {
		regServ.deleteRegistrationById(1L);
		
		assertFalse(regRep.existsById(1L));
		assertEquals(2, regServ.getAllRegistrations().size());
		
		regServ.deleteRegistrationById(2L);
		regServ.deleteRegistrationById(3L);
		
		assertFalse(regRep.existsById(2L));
		assertFalse(regRep.existsById(3L));
		assertEquals(0, regServ.getAllRegistrations().size());
	}

}
