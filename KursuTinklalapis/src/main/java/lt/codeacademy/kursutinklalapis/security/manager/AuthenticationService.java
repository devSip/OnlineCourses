package lt.codeacademy.kursutinklalapis.security.manager;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import lt.codeacademy.kursutinklalapis.entities.Role;
import lt.codeacademy.kursutinklalapis.entities.User;
import lt.codeacademy.kursutinklalapis.repositories.UserRepository;
import lt.codeacademy.kursutinklalapis.security.filter.JwtService;
import lt.codeacademy.kursutinklalapis.security.token.Token;
import lt.codeacademy.kursutinklalapis.security.token.TokenRepository;
import lt.codeacademy.kursutinklalapis.security.token.TokenType;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository repository;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	/**
	 * The register method is a part of an authentication/authorization system. It takes in a RegisterRequest object which contains the user's details such as firstname, lastname, email, and password. The method first checks if a user with the same email already exists in the database. If it does, it prints a message to the console. If the user doesn't exist in the database, the method creates a new User object using the details from the RegisterRequest object, encrypts the password, sets the user's role to Role.STUDENT, and saves the user to the database using the repository.save() method. Next, the method generates a JWT access token and a refresh token using the jwtService.generateToken() and jwtService.generateRefreshToken() methods respectively, and saves the access token and the user ID to the database using the saveUserToken() method. Finally, the method returns an AuthenticationResponse object containing the access token and the refresh token.
	 */
	public AuthenticationResponse register(RegisterRequest request) {
		if (repository.findByEmail(request.getEmail()).isPresent()) {
			System.out.println("taip");
			;
		}

		User user = User.builder().firstname(request.getFirstname()).lastname(request.getLastname())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(Role.STUDENT)
				.build();

		User savedUser = repository.save(user);
		String jwtToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		saveUserToken(savedUser, jwtToken);

		return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();

	}

	/**
	 *  Method authenticate takes an AuthenticationRequest object as input and returns an AuthenticationResponse object as output. First, the method calls authenticationManager.authenticate to authenticate the user's credentials. If the authentication is successful, the method retrieves the User object associated with the authenticated email address by calling repository.findByEmail. If the email address is not found, the method throws an exception. Next, the method generates a new JWT access token and refresh token for the user by calling jwtService.generateToken and jwtService.generateRefreshToken, respectively. Then, the method revokes all existing refresh tokens associated with the user by calling revokeAllUserTokens. This ensures that only the latest refresh token is valid, and previous refresh tokens cannot be used to generate new access tokens. Finally, the method saves the new refresh token in the database by calling saveUserToken and returns an AuthenticationResponse object containing the user ID, user role, access token, and refresh token.
	 */
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		User user = repository.findByEmail(request.getEmail()).orElseThrow();
		String jwtToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
		return AuthenticationResponse.builder().userId(user.getId()).userRole(user.getRole()).accessToken(jwtToken)
				.refreshToken(refreshToken).build();
	}

	/**
	 * This is a private method saveUserToken that takes a User object and a jwtToken string as parameters. It creates a new Token object with the provided User and jwtToken, sets the token type to TokenType.BEARER, and sets expired and revoked to false. Finally, it saves the Token object to the tokenRepository. This method is likely used to save a new token for a user after successful authentication or refresh of an existing token.
	 */
	private void saveUserToken(User user, String jwtToken) {
		Token token = Token.builder()
				.user(user)
				.userRole(user.getRole())
				.token(jwtToken)
				.tokenType(TokenType.BEARER)
				.expired(false)
				.revoked(false)
				.build();
		
		tokenRepository.save(token);
	}

	/**
	 * This method revokes all valid tokens belonging to a given user by setting their expired and revoked fields to true and then saving them using the tokenRepository.
	 */
	private void revokeAllUserTokens(User user) {
		List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}

	/**
	 * This method is used to refresh an access token using a refresh token. It takes in an HTTP servlet request and response as parameters. It first checks the Authorization header of the request to extract the refresh token. If a valid refresh token is found, it extracts the user's email address from it and checks if it is a valid token for that user. If it is a valid token, it generates a new access token and revokes all existing tokens for that user. It then saves the new token and returns an AuthenticationResponse object containing the new access token and the same refresh token. Finally, it writes the AuthenticationResponse object to the response output stream using the Jackson ObjectMapper.
	 */
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userEmail;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(refreshToken);
		if (userEmail != null) {
			User user = this.repository.findByEmail(userEmail).orElseThrow();
			if (jwtService.isTokenValid(refreshToken, user)) {
				String accessToken = jwtService.generateToken(user);
				revokeAllUserTokens(user);
				saveUserToken(user, accessToken);
				AuthenticationResponse authResponse = AuthenticationResponse.builder().accessToken(accessToken)
						.refreshToken(refreshToken).build();
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}
}
