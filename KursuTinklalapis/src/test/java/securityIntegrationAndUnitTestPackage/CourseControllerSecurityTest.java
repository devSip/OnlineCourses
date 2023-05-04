package securityIntegrationAndUnitTestPackage;

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

import lt.codeacademy.kursutinklalapis.controllers.*;
import lt.codeacademy.kursutinklalapis.security.filter.JwtAuthenticationFilter;
import lt.codeacademy.kursutinklalapis.services.CourseService;
import lt.codeacademy.kursutinklalapis.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseController.class)
@ContextConfiguration(classes = KursuTinklalapisApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CourseControllerSecurityTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private CourseService cServ;
	
	@MockBean
	private JwtAuthenticationFilter jwt;
	
	@Test
	@WithAnonymousUser
	public void testCreateCourseForbiden() throws Exception {

	    mockMvc.perform(post("/courses")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isForbidden());
	}
		
	@Test
	@WithAnonymousUser
	public void testGetCourseByIdUnauthorized() throws Exception {

	    mockMvc.perform(get("/courses/1")
	    		.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithAnonymousUser
	public void testUpdateCourseForbiden() throws Exception {

	    mockMvc.perform(put("/courses/1/update")
	    		.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isForbidden());
	}
	
	@Test
	@WithAnonymousUser
	public void testDeleteCourseForbiden() throws Exception {

	    mockMvc.perform(delete("/courses/1/delete")
	    		.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isForbidden());
	}
}
