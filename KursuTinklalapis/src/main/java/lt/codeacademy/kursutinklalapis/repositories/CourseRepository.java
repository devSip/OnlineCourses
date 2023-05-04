package lt.codeacademy.kursutinklalapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.codeacademy.kursutinklalapis.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
