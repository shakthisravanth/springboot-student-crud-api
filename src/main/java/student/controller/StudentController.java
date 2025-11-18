package student.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import student.dto.StudentRequest;
import student.dto.StudentResponse;
import student.entity.Student;
import student.service.StudentService;

import java.net.URI;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    /**
     * Create a new student.
     */
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request) {
        Student toCreate = mapToEntity(request);
        Student created = studentService.createStudent(toCreate);
        return ResponseEntity
                .created(URI.create("/api/students/" + created.getId()))
                .body(mapToResponse(created));
    }

    /**
     * Get a student by id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(mapToResponse(student));
    }

    /**
     * Get students with optional filters and pagination.
     */
    @GetMapping
    public ResponseEntity<Page<StudentResponse>> getStudents(
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) Integer yop,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "false") boolean includeInactive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDir, sortBy));
        Boolean activeFilter = active != null ? active : (includeInactive ? null : Boolean.TRUE);
        Page<StudentResponse> students = studentService.searchStudents(branch, yop, activeFilter, pageable)
                .map(this::mapToResponse);
        return ResponseEntity.ok(students);
    }

    /**
     * Update an existing student by id.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long id,
                                                         @Valid @RequestBody StudentRequest request) {
        Student updatedEntity = mapToEntity(request);
        Student updated = studentService.updateStudent(id, updatedEntity);
        return ResponseEntity.ok(mapToResponse(updated));
    }

    /**
     * Soft-deactivate a student (active = false).
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<StudentResponse> deactivateStudent(@PathVariable Long id) {
        Student deactivated = studentService.deactivateStudent(id);
        return ResponseEntity.ok(mapToResponse(deactivated));
    }

    /**
     * Permanently delete a student by id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Map request DTO to entity.
     */
    private Student mapToEntity(StudentRequest request) {
        Student student = new Student();
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setBranch(request.getBranch());
        student.setYop(request.getYop());
        student.setActive(request.getActive());
        return student;
    }

    /**
     * Map entity to response DTO.
     */
    private StudentResponse mapToResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .fullName(student.getFullName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .branch(student.getBranch())
                .yop(student.getYop())
                .active(student.getActive())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}


