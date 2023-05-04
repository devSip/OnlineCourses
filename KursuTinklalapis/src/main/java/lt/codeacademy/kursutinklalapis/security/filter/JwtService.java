package lt.codeacademy.kursutinklalapis.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lt.codeacademy.kursutinklalapis.security.SecurityConstants;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * The JwtService class is a Spring @Service component responsible for generating and validating JSON Web Tokens (JWTs) using the io.jsonwebtoken library. It has the following public methods: extractUsername(String token): Extracts the subject (username) from the provided JWT token; extractClaim(String token, Function<Claims, T> claimsResolver): Extracts a specific claim from the JWT token using a claimsResolver function; generateToken(UserDetails userDetails): Generates a JWT for the given userDetails, with no extra claims and using the default JWT expiration time defined in the SecurityConstants class; generateToken(Map<String, Object> extraClaims, UserDetails userDetails): Generates a JWT for the given userDetails, with extra claims specified in a Map and using the default JWT expiration time defined in the SecurityConstants class; generateRefreshToken(UserDetails userDetails): Generates a refresh token for the given userDetails, with no extra claims and using the default refresh token expiration time defined in the SecurityConstants class; isTokenValid(String token, UserDetails userDetails): Validates whether the provided JWT token is valid for the given userDetails; getSignInKey(): Returns the signing key used for generating and validating JWTs, which is derived from the SecurityConstants.SECRET_KEY value. The JwtService class also has private methods for extracting specific claims and all claims from a JWT token, checking if a token is expired, and building a JWT token with the specified claims and expiration time.
 */
@Service
public class JwtService {

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return buildToken(extraClaims, userDetails, SecurityConstants.JWT_EXPIRATION);
	}

	public String generateRefreshToken(UserDetails userDetails) {
		return buildToken(new HashMap<>(), userDetails, SecurityConstants.REFRES_EXPIRATION);
	}

	private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
