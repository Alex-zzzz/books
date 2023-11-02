package online.book.store.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotEmpty
        @Size(min = 4, max = 50)
        @Email
        String email,

        @NotBlank
        @Size(min = 6, max = 100)
        String password,

        @NotBlank @Size(min = 6, max = 100) String repeatPassword,

        @NotBlank String firstName,

        @NotBlank String lastName
) {
}
