package gr.aueb.cf.schoolspring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserInsertDTO(
        @NotNull(message = "Το email είναι υποχρεωτικό.")
        @Email(message = "Μη έγκυρη μορφή email.")
        String username,

        @NotNull(message = "Ο κωδικός είναι υποχρεωτικό.")
        @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$", message = "Invalid Password")
        String password
) {
}
