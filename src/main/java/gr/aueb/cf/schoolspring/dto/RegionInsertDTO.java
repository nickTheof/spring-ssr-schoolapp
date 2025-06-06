package gr.aueb.cf.schoolspring.dto;

import jakarta.validation.constraints.NotNull;

public record RegionInsertDTO(
        @NotNull(message = "Το όνομα περιοχής δεν μπορεί να είναι κενό.")
        String name
) {
}
