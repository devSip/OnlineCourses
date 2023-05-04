package applicationClassesNotNullTestsPackage;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import lt.codeacademy.kursutinklalapis.KursuTinklalapisApplication;
import lt.codeacademy.kursutinklalapis.controllers.CourseController;
import lt.codeacademy.kursutinklalapis.controllers.ProfessorController;
import lt.codeacademy.kursutinklalapis.controllers.RegistrationController;
import lt.codeacademy.kursutinklalapis.controllers.UserController;
import lt.codeacademy.kursutinklalapis.repositories.CourseRepository;
import lt.codeacademy.kursutinklalapis.repositories.ProfessorRepository;
import lt.codeacademy.kursutinklalapis.repositories.RegistrationRepository;
import lt.codeacademy.kursutinklalapis.repositories.UserRepository;
import lt.codeacademy.kursutinklalapis.services.CourseService;
import lt.codeacademy.kursutinklalapis.services.ProfessorService;
import lt.codeacademy.kursutinklalapis.services.RegistrationService;
import lt.codeacademy.kursutinklalapis.services.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = KursuTinklalapisApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ApplicationClassesNotNull {

	@Autowired
	private ProfessorController prfCon;
	@Autowired
	private CourseController courCon;
	@Autowired
	private RegistrationController regCon;
	@Autowired
	private UserController userCon;
	@Autowired
	private CourseService cServ;
	@Autowired
	private ProfessorService pServ;
	@Autowired
	private RegistrationService rServ;
	@Autowired
	private UserService uServ;
	@Autowired
	private CourseRepository cRep;
	@Autowired
	private ProfessorRepository pRep;
	@Autowired
	private RegistrationRepository rRep;
	@Autowired
	private UserRepository uRep;

	@Test
	@DisplayName("ProfessorController is not null")
	void professorControllerNotNullTest() {
		assertNotNull(prfCon);
	}

	@Test
	@DisplayName("CourseController is not null")
	void courseControllerNotNullTest() {
		assertNotNull(courCon);
	}

	@Test
	@DisplayName("RegistrationController is not null")
	void registrationControllerNotNullTest() {
		assertNotNull(regCon);
	}

	@Test
	@DisplayName("UserController is not null")
	void userControllerNotNullTest() {
		assertNotNull(userCon);
	}

	@Test
	@DisplayName("CourseService is not null")
	void courseServiceNotNullTest() {
		assertNotNull(cServ);
	}

	@Test
	@DisplayName("ProfessorService is not null")
	void professorServiceNotNullTest() {
		assertNotNull(pServ);
	}

	@Test
	@DisplayName("RegistrationService is not null")
	void registrationServiceNotNullTest() {
		assertNotNull(rServ);
	}

	@Test
	@DisplayName("UserService is not null")
	void userServiceNotNullTest() {
		assertNotNull(uServ);
	}

	@Test
	@DisplayName("CourseRepository is not null")
	void courseRepositoryNotNullTest() {
		assertNotNull(cRep);
	}

	@Test
	@DisplayName("ProfessorRepository is not null")
	void professorRepositoryNotNullTest() {
		assertNotNull(pRep);
	}

	@Test
	@DisplayName("RegistrationRepository is not null")
	void registrationRepositoryNotNullTest() {
		assertNotNull(rRep);
	}

	@Test
	@DisplayName("UserRepository is not null")
	void userRepositoryNotNullTest() {
		assertNotNull(uRep);
	}
}
