package gr.aueb.cf.schoolspring.dto;

import java.time.LocalDateTime;

public record UserReadOnlyDTO(
        Long id,
        String uuid,
        String username,
        String role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
