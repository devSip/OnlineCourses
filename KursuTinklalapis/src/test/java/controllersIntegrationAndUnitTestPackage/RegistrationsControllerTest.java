package controllersIntegrationAndUnitTestPackage;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lt.codeacademy.kursutinklalapis.KursuTinklalapisApplication;
import lt.codeacademy.kursutinklalapis.entities.Course;
import lt.codeacademy.kursutinklalapis.entities.Registration;
import lt.codeacademy.kursutinklalapis.entities.RegistrationRequestDto;
import lt.codeacademy.kursutinklalapis.entities.Role;
import lt.codeacademy.kursutinklalapis.entities.User;
import lt.codeacademy.kursutinklalapis.services.CourseService;
import lt.codeacademy.kursutinklalapis.services.RegistrationService;
import lt.codeacademy.kursutinklalapis.services.UserService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = KursuTinklalapisApplication.class)
class RegistrationsControllerTest {

	@Autowired
	private UserService uServ;
	
	@Autowired
	private CourseService cServ;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RegistrationService regServ;
	
	@Test
	public void createRegistrationTest() throws Exception {
	    User user1 = new User("Studentas1", "StudPavarde1", "studentas1@mail.com", "123", Role.STUDENT);
	    uServ.createStudent(user1);

	    Course course1 = new Course("Kursas1", "Mokslas1", "Profesorius1");
	    cServ.saveCourse(course1);

	    RegistrationRequestDto reg1 = new RegistrationRequestDto(user1.getId(), course1.getId());
	    Registration registration = new Registration(reg1, user1, course1);

	    when(regServ.createRegistration(reg1)).thenReturn(registration);

	    mockMvc.perform(MockMvcRequestBuilders.post("/api/registrations")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(reg1)))
	            .andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void getRegistrationByIdTest() throws Exception {
		User user1 = new User("Studentas1", "StudPavarde1", "studentas1@mail.com", "123", Role.STUDENT);
		uServ.createStudent(user1);

	    Course course1 = new Course("Kursas1", "Mokslas1", "Profesorius1");
	    cServ.saveCourse(course1);

	    RegistrationRequestDto reg1 = new RegistrationRequestDto(user1.getId(), course1.getId());
	    
	    Registration registration = new Registration(reg1, user1, course1);
	    
	    when(regServ.getRegistrationById(1L)).thenReturn(registration);

	    mockMvc.perform(MockMvcRequestBuilders.get("/api/registrations/{id}", 1L))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(registration.getId()))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.user.id").value(registration.getUser().getId()))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.course.id").value(registration.getCourse().getId()));
	}
	@Test
	@Transactional
	public void updateRegistration() throws Exception {
	    User user1 = new User(1L, "Studentas1", "StudPavarde1", "studentas1@mail.com", "123", Role.STUDENT);
	    User user2 = new User(1L, "Studentas2", "StudPavarde2", "studentas2@mail.com", "456", Role.STUDENT);

	    Course course1 = new Course(1L, "Kursas1", "Mokslas1", "Profesorius1");
	    Course course2 = new Course(1L, "Kursas2", "Mokslas2", "Profesorius2");

	    RegistrationRequestDto reg1 = new RegistrationRequestDto(user1.getId(), course1.getId());
	    RegistrationRequestDto reg2 = new RegistrationRequestDto(user2.getId(), course2.getId());

	    Registration registration1 = new Registration(reg1, user1, course1);
	    Registration registration2 = new Registration(reg2, user2, course2);

	    when(regServ.getRegistrationById(1L)).thenReturn(registration1);
	    when(regServ.updateRegistration(1L, registration2)).thenReturn(registration2);

	    String json = "{" +
	            "\"id\": 1," +
	            "\"user\": {" +
	                "\"id\": 1," +
	                "\"firstName\": \"Studentas2\"," +
	                "\"lastName\": \"StudPavarde2\"," +
	                "\"email\": \"studentas2@mail.com\"," +
	                "\"password\": \"456\"," +
	                "\"role\": \"STUDENT\"" +
	            "}," +
	            "\"course\": {" +
	                "\"id\": 1," +
	                "\"description\": \"Mokslas2\"," +
	                "\"subject\": \"Kursas2\"," +
	                "\"professorName\": \"Profesorius2\"" +
	            "}" +
	        "}";

	    mockMvc.perform(MockMvcRequestBuilders.put("/api/registrations/{id}/update", 1L)
	    		.content(json)
		        .contentType(MediaType.APPLICATION_JSON)
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void deleteRegistrationTest() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/registrations/{id}/delete", 1L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
				
	}
}


















