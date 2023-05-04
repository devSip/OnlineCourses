package lt.codeacademy.kursutinklalapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.codeacademy.kursutinklalapis.entities.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

}