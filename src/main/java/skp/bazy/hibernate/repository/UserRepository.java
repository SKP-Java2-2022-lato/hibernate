package skp.bazy.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skp.bazy.hibernate.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
