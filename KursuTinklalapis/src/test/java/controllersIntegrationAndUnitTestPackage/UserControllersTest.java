package controllersIntegrationAndUnitTestPackage;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import jakarta.transaction.Transactional;
import lt.codeacademy.kursutinklalapis.KursuTinklalapisApplication;
import lt.codeacademy.kursutinklalapis.services.UserService;
import lt.codeacademy.kursutinklalapis.entities.Role;
import lt.codeacademy.kursutinklalapis.entities.User;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = KursuTinklalapisApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserControllersTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService uServ;

	@Test
	public void getUserByIdTest() throws Exception {
		User u1 = new User(1L, "Studentas1", "StudPavarde1", "studentas1@mail.com", "123", Role.STUDENT);
		User u2 = new User(2L, "Studentas2", "StudPavarde2", "studentas2@mail.com", "456", Role.STUDENT);
		User u3 = new User(3L, "Studentas3", "StudPavarde3", "studentas3@mail.com", "789", Role.STUDENT);

		when(uServ.getStudentById(1L)).thenReturn(u1);
		when(uServ.getStudentById(2L)).thenReturn(u2);
		when(uServ.getStudentById(3L)).thenReturn(u3);

		mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}", 1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("Studentas1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("StudPavarde1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("studentas1@mail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.role").value("STUDENT"));

		mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}", 2))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("Studentas2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("StudPavarde2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("studentas2@mail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.role").value("STUDENT"));

		mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}", 3))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("Studentas3"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("StudPavarde3"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("studentas3@mail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.role").value("STUDENT"));
	}

	@Test
	public void createUserTest() throws Exception {
		User u1 = new User(1L, "Studentas1", "StudPavarde1", "studentas1@mail.com", "123", Role.STUDENT);
		when(uServ.createStudent(ArgumentMatchers.any(User.class))).thenReturn(u1);

		String newUser = "{\"id\":\"1\"," + "\"firstname\":\"Studentas1\"," + "\"lastname\":\"StudPavarde1\","
				+ "\"email\":\"studentas1@mail.com\"," + "\"password\":\"123\"," + "\"role\":\"STUDENT\"}";

		mockMvc.perform(
				MockMvcRequestBuilders.post("/students")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newUser))
				.andExpect(MockMvcResultMatchers.status()
				.isCreated());
	}

	@Test
	@Transactional
	public void updateUserTest() throws Exception {

		User u1 = new User(1L, "Studentas1", "StudPavarde1", "studentas1@mail.com", "123", Role.STUDENT);
		User u2 = new User(1L, "Studentas2", "StudPavarde2", "studentas2@mail.com", "123", Role.STUDENT);

		when(uServ.getStudentById(1L)).thenReturn(u1);
		when(uServ.updateStudent(1L, u2)).thenReturn(u2);

		String json = "{\"id\":1,\"firstname\":\"Studentas2\",\"lastname\":\"StudPavarde2\",\"email\":\"studentas2@mail.com\",\"password\":\"123\",\"role\":\"STUDENT\"}";

		mockMvc.perform(MockMvcRequestBuilders.put("/students/{id}/update", 1L)
		        .content(json)
		        .contentType(MediaType.APPLICATION_JSON)
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$.id").value(1))
		        .andExpect(jsonPath("$.firstname").value("Studentas2"))
		        .andExpect(jsonPath("$.lastname").value("StudPavarde2"))
		        .andExpect(jsonPath("$.email").value("studentas2@mail.com"))
		        .andExpect(jsonPath("$.role").value("STUDENT"));;
	}

	@Test
	public void deleteUserTest() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/students/{id}/delete", 1L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
