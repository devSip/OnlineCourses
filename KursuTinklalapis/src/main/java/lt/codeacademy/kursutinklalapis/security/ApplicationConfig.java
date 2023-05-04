package lt.codeacademy.kursutinklalapis.security;

import lombok.RequiredArgsConstructor;
import lt.codeacademy.kursutinklalapis.repositories.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final UserRepository repository;

	/**
	 * This is a method annotated with @Bean that returns an implementation of the UserDetailsService interface. The UserDetailsService interface is used by Spring Security to retrieve user details when authenticating users. The implementation returned by this method takes a username as a parameter and retrieves the corresponding user from a repository object using their email address. If the user is not found, a UsernameNotFoundException is thrown.
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> repository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	/**
	 * This method is a bean definition that returns an instance of AuthenticationProvider. An AuthenticationProvider is an interface in Spring Security that is responsible for authenticating a user. In this method, a DaoAuthenticationProvider is created and configured to use the userDetailsService() and passwordEncoder() methods for authentication. The userDetailsService() method returns an instance of the UserDetailsService interface, which is responsible for loading user-specific data. In this case, the implementation of UserDetailsService is likely to retrieve user data from a database. The passwordEncoder() method returns an instance of the PasswordEncoder interface, which is responsible for encoding passwords. In this case, the implementation of PasswordEncoder is likely to use a strong hashing algorithm to encode passwords securely. Once the DaoAuthenticationProvider is created and configured, it is returned as a bean. The AuthenticationProvider bean can be used in other parts of the application to authenticate users.
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	/**
	 * This method defines a bean for creating an instance of AuthenticationManager which is used for authenticating users in the application. The AuthenticationManager is created by passing an instance of AuthenticationConfiguration as a parameter to the method, which is used to obtain the authentication manager from the Spring Security framework. The AuthenticationManager is responsible for authenticating the user credentials, and it does so by delegating the authentication to one or more AuthenticationProvider instances. The AuthenticationManager bean is typically used in the Spring Security configuration to specify which authentication provider should be used for a given security configuration.	 
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	/**
	 * This method is a Spring Bean that creates and returns a PasswordEncoder instance using the BCrypt hashing algorithm. The PasswordEncoder is used to securely hash and store passwords for users in the application. This implementation is widely used and recommended for its strength and resistance to brute-force attacks.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
