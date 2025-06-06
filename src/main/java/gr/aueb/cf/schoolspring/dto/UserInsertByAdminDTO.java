package gr.aueb.cf.schoolspring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserInsertByAdminDTO(
        @NotNull(message = "Το email είναι υποχρεωτικό.")
        @Email(message = "Μη έγκυρη μορφή email.")
        String username,

        @NotNull(message = "Ο κωδικός είναι υποχρεωτικός.")
        @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$", message = "Μη έγκυρη μορφή κωδικού.")
        String password,

        @NotNull(message = "Η επιβεβαίωση κωδικού είναι υποχρεωτική.")
        @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$", message = "Μη έγκυρη μορφή επιβεβαίωσης κωδικού.")
        String confirmPassword,

        @NotNull(message = "Το status του user είναι υποχρεωτικό.")
        Boolean isActive,

        @NotNull(message = "Το ρόλος του user είναι υποχρεωτικός.")
        @Pattern(regexp = "^(ADMIN|EDITOR|READER)$")
        String role

) {
}
