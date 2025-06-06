package gr.aueb.cf.schoolspring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserUpdateDTO(
        String uuid,
        @NotNull(message = "Το email είναι υποχρεωτικό.")
        @Email(message = "Μη έγκυρη μορφή email.")
        String username,

        @NotNull(message = "Ο κωδικός είναι υποχρεωτικό.")
        @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$", message = "Invalid Password")
        String password,

        @NotNull(message = "Το πεδίο isActive είναι υποχρεωτικό.")
        Boolean isActive,

        @NotNull(message = "Ο ρόλος είναι υποχρεωτικό πεδίο")
        @Pattern(regexp = "^(EDITOR|READER|ADMIN)$", message = "Ο ρόλος μπορεί να είναι READER, EDITOR, ή ADMIN")
        String role
) {
}
