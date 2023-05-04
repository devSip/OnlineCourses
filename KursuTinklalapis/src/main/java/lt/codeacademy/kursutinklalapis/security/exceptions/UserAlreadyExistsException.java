package lt.codeacademy.kursutinklalapis.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistsException extends ResponseStatusException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException() {
		super(HttpStatus.BAD_REQUEST, "User already exists");
	}
}