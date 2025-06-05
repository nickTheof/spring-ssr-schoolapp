package gr.aueb.cf.schoolspring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TeacherUpdateDTO(
        String uuid,
        @NotNull(message = "Το όνομα δεν μπορεί να είναι κενό.")
        @Size(min = 2, message = "Το όνομα πρέπει να περιέχει τουλάχιστον δυο χαρακτήρες.")
        String firstname,

        @NotNull(message = "Το επώνυμο δεν μπορεί να είναι κενό.")
        @Size(min = 2, message = "Το επώνυμο πρέπει να περιέχει τουλάχιστον δυο χαρακτήρες.")
        String lastname,

        @NotNull(message = "Το ΑΦΜ δεν μπορεί να είναι κενό.")
        @Pattern(regexp = "^\\d{9,}$", message = "Το ΑΦΜ πρέπει να αποτελείται απο τουλάχιστον εννέα ψηφία.")
        String vat,

        @NotNull(message = "Το email δεν μπορεί να είναι κενό.")
        @Email(message = "Το email δεν έχει έγκυρη μορφή.")
        String email,

        @NotNull(message = "Το όνομα πατρός δεν μπορεί να είναι κενό.")
        @Size(min = 2, message = "Το όνομα πατρός πρέπει να περιέχει τουλάχιστον δυο χαρακτήρες.")
        String fatherName,

        @NotNull(message = "Η οδός δεν μπορεί να είναι κενή.")
        @Size(min = 2, message = "Η οδός πρέπει να περιέχει τουλάχιστον δυο χαρακτήρες.")
        String street,

        @NotNull(message = "Ο αριθμός διεύθυνσης δεν μπορεί να είναι κενός.")
        String streetNum,

        @NotNull(message = "Το ΤΚ δεν μπορεί να είναι κενό.")
        @Size(min = 5, message = "Το ΤΚ πρέπει να περιέχει τουλάχιστον πέντε χαρακτήρες.")
        String zipCode,

        @NotNull(message = "Η περιοχή δεν μπορεί να είναι κενή.")
        Integer regionId
) {
}
