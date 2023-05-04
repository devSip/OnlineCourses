package securityIntegrationAndUnitTestPackage;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import lt.codeacademy.kursutinklalapis.KursuTinklalapisApplication;
import lt.codeacademy.kursutinklalapis.controllers.ProfessorController;
import lt.codeacademy.kursutinklalapis.security.filter.JwtAuthenticationFilter;
import lt.codeacademy.kursutinklalapis.services.ProfessorService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfessorController.class)
@ContextConfiguration(classes = KursuTinklalapisApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProfessorControllerSecurityTest {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private JwtAuthenticationFilter jwt;
	
	@MockBean
	private ProfessorService profServ;
	
	@Test
	@WithAnonymousUser
	public void testCreateProfessorForbiden() throws Exception {

	    mockMvc.perform(post("/professors")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isForbidden());
	}
	
	@Test
	@WithAnonymousUser
	public void testGetProfessorByIdUnauthorized() throws Exception {
		
	    mockMvc.perform(get("/professors/1")
	    		.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithAnonymousUser
	public void testUpdateProfessorForbiden() throws Exception {
		
	    mockMvc.perform(put("/professors/1/update")
	    		.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isForbidden());
	}
	
	@Test
	@WithAnonymousUser
	public void testDeleteProfessorForbiden() throws Exception {
		
	    mockMvc.perform(delete("/professors/1/delete")
	    		.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isForbidden());
	}

}
