package gr.aueb.cf.schoolspring.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegionInsertDTO(
        @NotNull(message = "Το όνομα περιοχής δεν μπορεί να είναι κενό.")
        @Size(min = 2, message = "Το όνομα πρέπει να περιέχει τουλάχιστον 2 χαρακτήρες")
        String name
) {
}
