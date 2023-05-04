package lt.codeacademy.kursutinklalapis.security;

import lombok.RequiredArgsConstructor;
import lt.codeacademy.kursutinklalapis.entities.Role;
import lt.codeacademy.kursutinklalapis.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.Arrays;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfiguration {
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;
	private final LogoutHandler logoutHandler;

	/**
	 * This method configures the security settings for an HTTP end-point in a Spring Boot application using Spring Security. The method first disables the frame options and CSRF protection. It then specifies which HTTP requests can be accessed by which roles using the requestMatchers() method, and the hasRole() and hasAnyRole() methods. It also sets the session creation policy to STATELESS and adds authentication providers and filters. Finally, it configures the logout URL and handlers and clears the security context after a successful logout. In summary, this method defines the rules for who can access which endpoints and how authentication and authorization are handled for those end-points.
	*/
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.headers().frameOptions().disable()
		.and()
		.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
		.requestMatchers(HttpMethod.GET, SecurityConstants.GET_COURSES, SecurityConstants.GET_PROFESSORS).permitAll()
		.requestMatchers(HttpMethod.GET, SecurityConstants.GET_STUDENTS).hasAnyRole(Role.ADMIN, Role.PROFESSOR, Role.STUDENT)
		.requestMatchers(HttpMethod.GET, SecurityConstants.GET_REGISTRATIONS).hasAnyRole(Role.ADMIN, Role.PROFESSOR, Role.STUDENT)
		.requestMatchers(HttpMethod.PUT, SecurityConstants.UPDATE_STUDENTS).hasRole(Role.ADMIN)
		.requestMatchers(HttpMethod.PUT, SecurityConstants.UPDATE_COURSES, SecurityConstants.UPDATE_PROFESSORS).hasRole(Role.ADMIN)
		.requestMatchers(HttpMethod.PUT, SecurityConstants.UPDATE_REGISTRATIONS).hasRole(Role.ADMIN)
		.requestMatchers(HttpMethod.POST, SecurityConstants.ADD_COURSES, SecurityConstants.ADD_PROFESSORS).hasRole(Role.ADMIN)						
		.requestMatchers(HttpMethod.POST, SecurityConstants.ADD_REGISTRATIONS).hasRole(Role.STUDENT)
		.requestMatchers(HttpMethod.DELETE, SecurityConstants.DELETE_STUDENTS, SecurityConstants.DELETE_PROFESSORS, SecurityConstants.DELETE_COURSES).hasRole(Role.ADMIN)
		.requestMatchers(HttpMethod.DELETE, SecurityConstants.DELETE_REGISTRATIONS).hasAnyRole(Role.ADMIN, Role.STUDENT)
		.anyRequest().authenticated()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).logout()
		.logoutUrl("/api/v1/auth/logout").addLogoutHandler(logoutHandler)
		.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());

		return http.build();
	}
}
