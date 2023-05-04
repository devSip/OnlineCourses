package lt.codeacademy.kursutinklalapis.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.kursutinklalapis.security.token.TokenRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 * The LogoutService class is a Spring @Service component that implements the LogoutHandler interface. It provides the implementation for logging out a user by invalidating the JWT token. The logout method is the main method of the class and is called when a user logs out. It takes three parameters: a HttpServletRequest, a HttpServletResponse, and an Authentication object. It first retrieves the JWT token from the Authorization header of the request. If the token is not present or does not start with the "Bearer " prefix, it returns without doing anything. If the token is present, it is retrieved from the TokenRepository. If the token is found, it is marked as expired and revoked by setting the expired and revoked fields to true. The updated token is then saved to the TokenRepository. Finally, the security context is cleared by calling SecurityContextHolder.clearContext(), which removes the authentication information from the thread-local storage of the current thread.
 */
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

	private final TokenRepository tokenRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		jwt = authHeader.substring(7);
		var storedToken = tokenRepository.findByToken(jwt).orElse(null);
		if (storedToken != null) {
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokenRepository.save(storedToken);
			SecurityContextHolder.clearContext();
		}
	}
}
