package lt.codeacademy.kursutinklalapis.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lt.codeacademy.kursutinklalapis.security.token.TokenRepository;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * This class extends OncePerRequestFilter. It is used to intercept incoming
 * HTTP requests and authenticate users based on the presence of a JWT (JSON Web
 * Token) in the Authorization header of the request. The class has three
 * fields: jwtService: an instance of the JwtService class, which is used to
 * extract and validate JWTs; userDetailsService: an instance of the
 * UserDetailsService class, which is used to retrieve user details (such as
 * username and password) from the database; tokenRepository: an instance of the
 * TokenRepository class, which is used to retrieve and manage JWTs that have
 * been issued to users.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final TokenRepository tokenRepository;

	/**
	 * The doFilterInternal method is called for each incoming HTTP request. This
	 * method first checks whether the request path contains "user/authenticate". If
	 * it does, the method simply allows the request to proceed to the next filter
	 * in the chain. Otherwise, the method extracts the JWT from the Authorization
	 * header, and uses the JwtService instance to validate the token. If the token
	 * is valid, the method retrieves user details from the database using the
	 * UserDetailsService instance, and creates a new instance of the
	 * UsernamePasswordAuthenticationToken class. Finally, the method sets the
	 * SecurityContext with the authentication token and allows the request to
	 * proceed to the next filter in the chain.
	 */
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		if (request.getServletPath().contains("user/authenticate")) {
			filterChain.doFilter(request, response);
			return;
		}
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwt = authHeader.substring(7);
		userEmail = jwtService.extractUsername(jwt);
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
			var isTokenValid = tokenRepository.findByToken(jwt).map(t -> !t.isExpired() && !t.isRevoked())
					.orElse(false);
			if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}