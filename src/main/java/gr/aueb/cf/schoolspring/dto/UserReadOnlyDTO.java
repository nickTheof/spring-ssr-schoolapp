package gr.aueb.cf.schoolspring.dto;

import java.time.LocalDateTime;

public record UserReadOnlyDTO(
        Long id,
        String uuid,
        String username,
        String role,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
