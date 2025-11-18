package student.exception;

/**
 * Thrown when a student cannot be found for a given identifier.
 */
public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(Long id) {
        super("Student not found with id: " + id);
    }
}


