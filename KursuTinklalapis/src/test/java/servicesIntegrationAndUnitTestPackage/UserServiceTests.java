package servicesIntegrationAndUnitTestPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import lt.codeacademy.kursutinklalapis.KursuTinklalapisApplication;
import lt.codeacademy.kursutinklalapis.entities.Role;
import lt.codeacademy.kursutinklalapis.entities.User;
import lt.codeacademy.kursutinklalapis.repositories.UserRepository;
import lt.codeacademy.kursutinklalapis.services.UserService;


@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = KursuTinklalapisApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(properties = {
	    "spring.datasource.url=jdbc:h2:mem:testdb" + "${random.uuid}?mode=MySQL",
	    "spring.datasource.driver-class-name=org.h2.Driver",
	    "spring.datasource.username=one",
	    "spring.datasource.password=",
	    "spring.jpa.hibernate.ddl-auto=create-drop"
	})
class UserServiceTests {

	@Autowired
	private UserService uServ;
	
	@Autowired
	private UserRepository uRep;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Test
	@Order(1)
	void createAndCheckUsersTest() {
		
		uServ.createStudent(new User("Studentas1", "StudPavarde1", "studentas1@mail.com", "123", Role.STUDENT));
		uServ.createStudent(new User("Studentas2", "StudPavarde2", "studentas2@mail.com", "456", Role.STUDENT));
		uServ.createStudent(new User("Studentas3", "StudPavarde3", "studentas3@mail.com", "789", Role.STUDENT));
		
		assertTrue(uServ.getStudentById(1L).isEnabled());
		assertTrue(uServ.getStudentById(2L).isEnabled());
		assertTrue(uServ.getStudentById(3L).isEnabled());
		assertEquals(3 , uServ.getAllStudents().size());
		assertEquals("Studentas1", uServ.getStudentById(1L).getFirstname());
		assertEquals("StudPavarde2", uServ.getStudentById(2L).getLastname());
		assertEquals("studentas3@mail.com", uServ.getStudentById(3L).getEmail());
		assertFalse(uServ.getStudentById(1L).getPassword().isBlank());
		assertFalse(uServ.getStudentById(2L).getPassword().isBlank());
		assertFalse(uServ.getStudentById(3L).getPassword().isBlank());
	}
	@Test
	@Order(2)
	void updateAndCheckUserTest() {
		
		User upStud1 = uServ.getStudentById(1L);
		upStud1.setFirstname("newName1");
		upStud1.setLastname("newLastName1");
		upStud1.setEmail("new1@mail.com");
		upStud1.setPassword("147");
		uServ.updateStudent(1L, upStud1);
		
		User upStud2 = uServ.getStudentById(2L);
		upStud2.setFirstname("newName2");
		upStud2.setLastname("newLastName2");
		upStud2.setEmail("new2@mail.com");
		upStud2.setPassword("258");
		uServ.updateStudent(2L, upStud2);
		
		User upStud3 = uServ.getStudentById(3L);
		upStud3.setFirstname("newName3");
		upStud3.setLastname("newLastName3");
		upStud3.setEmail("new3@mail.com");
		upStud3.setPassword("369");
		uServ.updateStudent(3L, upStud3);
		
		assertTrue(uServ.getStudentById(1L).isEnabled());
		assertTrue(uServ.getStudentById(2L).isEnabled());
		assertTrue(uServ.getStudentById(3L).isEnabled());
		assertEquals(3 , uServ.getAllStudents().size());
		assertEquals("newName1", uServ.getStudentById(1L).getFirstname());
		assertEquals("newLastName2", uServ.getStudentById(2L).getLastname());
		assertEquals("new3@mail.com", uServ.getStudentById(3L).getEmail());
		assertFalse(uServ.getStudentById(1L).getPassword().isBlank());
		assertFalse(uServ.getStudentById(2L).getPassword().isBlank());
		assertFalse(uServ.getStudentById(3L).getPassword().isBlank());
	}
	@Test
	@Order(3)
	void deleteAndCheckUserTest() {
		uServ.deleteStudentById(3L);
		
		assertFalse(uRep.existsById(3L));
		assertEquals(2, uServ.getAllStudents().size());
		
		uServ.deleteStudentById(1L);
		uServ.deleteStudentById(2L);
		
		assertFalse(uRep.existsById(1L));
		assertFalse(uRep.existsById(2L));
		assertEquals(0, uServ.getAllStudents().size());
	}

}
