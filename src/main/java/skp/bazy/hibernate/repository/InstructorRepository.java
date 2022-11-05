package skp.bazy.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skp.bazy.hibernate.model.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
