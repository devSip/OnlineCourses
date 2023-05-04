package controllersIntegrationAndUnitTestPackage;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
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
import lt.codeacademy.kursutinklalapis.services.ProfessorService;
import lt.codeacademy.kursutinklalapis.entities.Professor;
import lt.codeacademy.kursutinklalapis.security.filter.JwtService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = KursuTinklalapisApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProfessorControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProfessorService profServ;
	
	 @MockBean
	 private JwtService jwtService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void getProfessorByIdTest() throws Exception {
		Professor prof1 = new Professor(1L,"fizika@mail.com", "Destytojas");
		Professor prof2 = new Professor(2L,"matematika@mail.com", "Mokytojas");
		
		when(profServ.getProfessorById(2L)).thenReturn(prof2);
		when(profServ.getProfessorById(1L)).thenReturn(prof1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/professors/{id}", 2))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
		.andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Mokytojas"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("matematika@mail.com"));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/professors/{id}", 1))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
		.andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Destytojas"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("fizika@mail.com"));
		
	}
	
	@Test
	public void createProfessorTest() throws Exception {
		Professor prof2 = new Professor(2L,"matematika@mail.com", "Mokytojas");
		when(profServ.createProfessor(ArgumentMatchers.any(Professor.class))).thenReturn(prof2);

		String newProfessor = "{\"id\":\"2\"," + "\"fullName\":\"Mokytojas\"," + "\"email\":\"matematika@mail.com\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/professors")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newProfessor))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Mokytojas"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("matematika@mail.com"));
	}
	
	@Test
	public void updateProfessorTest() throws Exception {
		
		Professor prof1 = new Professor(1L,"fizika@mail.com", "Destytojas");
		Professor prof2 = new Professor(1L,"matematika@mail.com", "Mokytojas");

		Mockito.when(profServ.getProfessorById(Mockito.any())).thenReturn(prof1);
		Mockito.when(profServ.updateProfessor(Mockito.any(), Mockito.any(Professor.class))).thenReturn(prof2);
		
		 String professorJson = objectMapper.writeValueAsString(prof2);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/professors/{id}/update", 1)
				.content(professorJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Mokytojas"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("matematika@mail.com"));
		
	}
	
	@Test
	public void deleteProfessorTest() throws Exception {
		 	
		    mockMvc.perform(MockMvcRequestBuilders.delete("/professors/{id}/delete", 1)
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(MockMvcResultMatchers.status().isOk());
		            
		            
	}

}
