package student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Response DTO for exposing student details.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String branch;
    private Integer yop;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
