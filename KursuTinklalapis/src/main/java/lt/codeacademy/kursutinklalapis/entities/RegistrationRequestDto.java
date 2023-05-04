package lt.codeacademy.kursutinklalapis.entities;

public class RegistrationRequestDto {
	private Long userId;
	private Long courseId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public RegistrationRequestDto(Long userId, Long courseId) {
		super();
		this.userId = userId;
		this.courseId = courseId;
	}
	
}