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
import lt.codeacademy.kursutinklalapis.controllers.RegistrationController;
import lt.codeacademy.kursutinklalapis.security.filter.JwtAuthenticationFilter;
import lt.codeacademy.kursutinklalapis.services.RegistrationService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RegistrationController.class)
@ContextConfiguration(classes = KursuTinklalapisApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class RegistrationControllerSecurityTest {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private JwtAuthenticationFilter jwt;
	
	@MockBean
	private RegistrationService regServ;
	
	@Test
	@WithAnonymousUser
	public void testCreateRegistrationForbiden() throws Exception {
		
	    mockMvc.perform(post("/api/registrations")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isForbidden());
	}
		
	@Test
	@WithAnonymousUser
	public void testGetRegistrationByIdUnauthorized() throws Exception {
		
		mockMvc.perform(get("/api/registrations/1")
	    		.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithAnonymousUser
	public void testUpdateRegistrationForbiden() throws Exception {
		
		mockMvc.perform(put("/api/registrations/1/update")
	    		.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isForbidden());
	}
	
	@Test
	@WithAnonymousUser
	public void testDeleteRegistrationForbiden() throws Exception {
		
		mockMvc.perform(delete("/api/registrations/1/delete")
	    		.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isForbidden());
	}

}
