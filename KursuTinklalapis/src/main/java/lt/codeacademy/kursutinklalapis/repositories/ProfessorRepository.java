package lt.codeacademy.kursutinklalapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.codeacademy.kursutinklalapis.entities.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

}