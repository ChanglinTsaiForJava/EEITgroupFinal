package eeit.OldProject.steve.DTO;


import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class RegisterRequestDTO {

    @NotBlank
    private String userAccount;

    @NotBlank
    private String userPassword;

    @NotBlank
    private String userName;

    @Email
    @NotBlank
    private String emailAddress;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String address;
}