package lt.codeacademy.kursutinklalapis.security;

/**
 *These constants are used to define the security rules and restrictions of the application, such as which end-points are accessible to which roles and which HTTP methods are allowed on each endpoint.
 */
public class SecurityConstants {
	public static final String SECRET_KEY = "bQeThWmZq4t7wzCFJNcRfUjXn2r5u8xADGKaPdSgVkYp3s6v9yBE)";
	public static final int JWT_EXPIRATION = 7200000;
	public static final int REFRES_EXPIRATION = 7200000;
	public static final String REGISTER_PATH = "/user/**";
	public static final String GET_COURSES = "/courses/**";
	public static final String GET_PROFESSORS = "/professors/**";
	public static final String GET_STUDENTS = "/students/**";
	public static final String GET_REGISTRATIONS = "/api/registrations/**";
	public static final String UPDATE_STUDENTS = "/students/*/update";
	public static final String UPDATE_COURSES = "/courses/*/update";
	public static final String UPDATE_PROFESSORS = "/professors/*/update";
	public static final String UPDATE_REGISTRATIONS = "/api/registrations/*/update";
	public static final String ADD_COURSES = "/courses";
	public static final String ADD_PROFESSORS = "/professors";
	public static final String ADD_STUDENTS = "/students";
	public static final String ADD_REGISTRATIONS = "/api/registrations";
	public static final String DELETE_STUDENTS = "/students/*/delete";
	public static final String DELETE_PROFESSORS = "/professors/*/delete";
	public static final String DELETE_COURSES = "/courses/*/delete";
	public static final String DELETE_REGISTRATIONS = "/api/registrations/*/delete";

}
