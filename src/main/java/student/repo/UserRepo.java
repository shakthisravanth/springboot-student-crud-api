package student.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import student.entity.User;

import java.util.Optional;

/**
 * Repository for User entity.
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    
    /**
     * Find user by email.
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if user exists by email.
     */
    boolean existsByEmail(String email);
}

