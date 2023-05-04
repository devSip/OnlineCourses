package controllersIntegrationAndUnitTestPackage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import lt.codeacademy.kursutinklalapis.KursuTinklalapisApplication;
import lt.codeacademy.kursutinklalapis.entities.Course;
import lt.codeacademy.kursutinklalapis.services.CourseService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = KursuTinklalapisApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CourseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseService cServ;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void getCourseByIdTest() throws Exception {

		when(cServ.getCourseById(1L)).thenReturn(new Course(1L, "Fizika", "Fiziniai desniai", "Fizikas"));
		when(cServ.getCourseById(2L)).thenReturn(new Course(2L, "Matematika", "Algebra", "Matematikas"));

		mockMvc.perform(MockMvcRequestBuilders.get("/courses/{id}", 2))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.subject").value("Matematika"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Algebra"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.professorName").value("Matematikas"));

		mockMvc.perform(MockMvcRequestBuilders.get("/courses/{id}", 1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.subject").value("Fizika"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Fiziniai desniai"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.professorName").value("Fizikas"));
	}

	@Test
	public void createCourseTest() throws Exception {
		Course cr = new Course(2L, "Matematika", "Algebra", "Matematikas");
		when(cServ.saveCourse(ArgumentMatchers.any(Course.class))).thenReturn(cr);

		String newCourse = "{\"id\":\"2\"," + "\"subject\":\"Matematika\"," + "\"description\":\"Algebra\","
				+ "\"professorName\":\"Matematikas\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/courses")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newCourse))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.subject").value("Matematika"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Algebra"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.professorName").value("Matematikas"));

	}

	@Test
	public void updateCourseTest() throws Exception {

		Course cr1 = new Course(1L, "Matematika", "Algebra", "Matematikas");
		Course cr2 = new Course(1L, "Fizika", "Fiziniai desniai", "Fizikas");

		when(cServ.getCourseById(any())).thenReturn(cr1);
		when(cServ.updateCourse(any(), any(Course.class))).thenReturn(cr2);
				
		mockMvc.perform(MockMvcRequestBuilders.put("/courses/{id}/update", 1L)
				.contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(cr2)))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.subject").value("Fizika"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Fiziniai desniai"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.professorName").value("Fizikas"));
		
	}

	@Test
	public void deleteCourseTest() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/courses/{id}/delete", 2L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}
}


