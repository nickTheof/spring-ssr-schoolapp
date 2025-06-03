package gr.aueb.cf.schoolspring.dto;

import java.time.LocalDateTime;

public record TeacherReadOnlyDTO(
        Long id,
        String uuid,
        String firstname,
        String lastname,
        String vat,
        String email,
        String fatherName,
        String street,
        String streetNum,
        String zipCode,
        String region,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
