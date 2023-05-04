package lt.codeacademy.kursutinklalapis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lt.codeacademy.kursutinklalapis.entities.Course;
import lt.codeacademy.kursutinklalapis.repositories.CourseRepository;

@Service
@Transactional
public class CourseService {
	@Autowired
	private CourseRepository courseRep;

	/**
	 * This is a public Java method that returns a list of all the courses in the database. It retrieves the courses from the database using the findAll method of the Course repository object (courseRep), and returns them as a list of Course objects. This method assumes that the courseRep object has been instantiated and initialized properly and that the database connection is available. If there are no courses in the database, this method will return an empty list. The returned list can be used to display all courses available in the system or to perform any other operation that requires accessing all courses.
	 */
	public List<Course> getAllCourses() {
		return courseRep.findAll();
	}

	/**
	 * This is a public Java method that retrieves a Course object from the database by its ID. The method takes a single parameter id, which is the unique identifier of the Course that needs to be retrieved. It uses the findById method of the Course repository object (courseRep) to retrieve the Course object from the database with the given id. If the Course object is found, it is returned by the method. If the Course object is not found, the method throws an EntityNotFoundException with a message "Course not found". This method assumes that the courseRep object has been instantiated and initialized properly and that the database connection is available. This method can be used to retrieve a Course object from the database by its ID, for example, when a user selects a course to view its details or register for the course.
	 */
	public Course getCourseById(Long id) {
		return courseRep.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));
	}

	/**
	 * This is a public Java method that saves a Course object to the database. The method takes a single parameter course, which is the Course object to be saved to the database. It uses the save method of the Course repository object (courseRep) to persist the Course object to the database. If the Course object does not exist in the database, it will be inserted as a new record. If the Course object already exists in the database, its values will be updated with the new values. This method assumes that the courseRep object has been instantiated and initialized properly and that the database connection is available. This method can be used to add a new Course to the database or update an existing Course in the database. For example, when an administrator creates a new course or updates an existing course information.
	 */
	public Course saveCourse(Course course) {
		return courseRep.save(course);
	}

	/**
	 * This is a public Java method that updates an existing Course object in the database. The method takes two parameters - id, which is the unique identifier of the Course object that needs to be updated, and courseDetails, which is the updated Course object. The method first uses the findById method of the Course repository object (courseRep) to retrieve the Course object from the database with the given id. If the Course object is found, it is stored in the course variable. If the Course object is not found, the method throws an EntityNotFoundException with a message "Course not found". The method then updates the values of the Course object with the values from courseDetails. Specifically, it sets the subject, description, and professor name of the course object to the corresponding values from courseDetails. Finally, the method uses the save method of the Course repository object to persist the updated course object to the database. The updated Course object is then returned by the method. This method assumes that the courseRep object has been instantiated and initialized properly and that the database connection is available. This method can be used to update the values of an existing Course object in the database. For example, when an administrator wants to update the details of a course such as the course subject, description, or professor name.
	 */
	public Course updateCourse(Long id, Course courseDetails) {
		Course course = courseRep.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));
		course.setSubject(courseDetails.getSubject());
		course.setDescription(courseDetails.getDescription());
		course.setProfessorName(courseDetails.getProfessorName());

		return courseRep.save(course);
	}

	/**
	 * This is a public Java method that deletes a Course object from the database with the given id. The method takes one parameter, id, which is the unique identifier of the Course object that needs to be deleted. The method uses the deleteById method of the Course repository object (courseRep) to delete the Course object from the database with the given id. This method assumes that the courseRep object has been instantiated and initialized properly and that the database connection is available. This method can be used to delete an existing Course object from the database. For example, when an administrator wants to remove a course from the system because it is no longer offered.
	 */
	public void deleteCourseById(Long id) {
		courseRep.deleteById(id);
	}
}
