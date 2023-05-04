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
import lt.codeacademy.kursutinklalapis.controllers.UserController;
import lt.codeacademy.kursutinklalapis.security.filter.JwtAuthenticationFilter;
import lt.codeacademy.kursutinklalapis.services.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = KursuTinklalapisApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserControllerSecurityTest {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private JwtAuthenticationFilter jwt;
	
	@MockBean
	private UserService uServ;
	
	@Test
	@WithAnonymousUser
	public void testCreateUserForbiden() throws Exception {
		
	    mockMvc.perform(post("/students")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isForbidden());
	}
		
	@Test
	@WithAnonymousUser
	public void testGetUserByIdUnauthorized() throws Exception {
		
		mockMvc.perform(get("/students/1")
	    		.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithAnonymousUser
	public void testUpdateUserForbiden() throws Exception {
		
		mockMvc.perform(put("/students/1/update")
	    		.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isForbidden());
	}
	
	@Test
	@WithAnonymousUser
	public void testDeleteUserForbiden() throws Exception {
		
		mockMvc.perform(delete("/students/1/delete")
	    		.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isForbidden());
	}

}
