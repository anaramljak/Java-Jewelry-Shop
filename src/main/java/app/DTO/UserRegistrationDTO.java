package app.DTO;

import app.Validators.PasswordMatches;
import app.Validators.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class UserRegistrationDTO {
    private UUID id;
    private String name;
    private String surname;
    @Email
    private String email;
    private String password;
    private String confirmPassword;
}
