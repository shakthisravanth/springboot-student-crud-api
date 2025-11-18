package student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import student.entity.Student;
import student.exception.StudentNotFoundException;
import student.repo.StudentRepo;
import student.spec.StudentSpecifications;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepo studentRepo;

    /**
     * Create and save a new student.
     */
    public Student createStudent(Student student) {
        // ensure ID is null so JPA treats this as a new entity
        student.setId(null);
        if (student.getActive() == null) {
            student.setActive(Boolean.TRUE);
        }
        return studentRepo.save(student);
    }

    /**
     * Get a student by id.
     */
    public Student getStudentById(Long id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    /**
     * Get all students.
     */
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    /**
     * Get students with optional filters and pagination.
     */
    public Page<Student> searchStudents(String branch, Integer yop, Boolean active, Pageable pageable) {
        var spec = (Specification<Student>) (root, query, builder) -> builder.conjunction();

        var branchSpec = StudentSpecifications.branchEquals(branch);
        if (branchSpec != null) {
            spec = spec.and(branchSpec);
        }

        var yopSpec = StudentSpecifications.yopEquals(yop);
        if (yopSpec != null) {
            spec = spec.and(yopSpec);
        }

        var activeSpec = StudentSpecifications.isActive(active);
        if (activeSpec != null) {
            spec = spec.and(activeSpec);
        }

        return studentRepo.findAll(spec, pageable);
    }

    /**
     * Update an existing student by id.
     */
    public Student updateStudent(Long id, Student updated) {
        Student existing = getStudentById(id);
        existing.setFullName(updated.getFullName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setBranch(updated.getBranch());
        existing.setYop(updated.getYop());
        if (updated.getActive() != null) {
            existing.setActive(updated.getActive());
        }
        return studentRepo.save(existing);
    }

    /**
     * Soft-deactivate a student (active = false).
     */
    public Student deactivateStudent(Long id) {
        Student existing = getStudentById(id);
        existing.setActive(Boolean.FALSE);
        return studentRepo.save(existing);
    }

    /**
     * Permanently delete a student by id.
     */
    public void deleteStudent(Long id) {
        Student existing = getStudentById(id);
        existing.setActive(Boolean.FALSE);
        studentRepo.save(existing);
    }
}
