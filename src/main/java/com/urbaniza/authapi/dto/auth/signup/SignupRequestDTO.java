package com.urbaniza.authapi.dto.auth.signup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequestDTO {

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 50, message = "Email deve ter no máximo 50 caracteres")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String password;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 50, message = "Nome deve ter no máximo 50 caracteres")
    private String firstName;

    @NotBlank(message = "Sobrenome é obrigatório")
    @Size(max = 50, message = "Sobrenome deve ter no máximo 50 caracteres")
    private String lastName;

    @NotBlank(message = "Role é obrigatória")
    private String role;

    public SignupRequestDTO() {
    }

    public SignupRequestDTO(String email, String password, String firstName, String lastName, String role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}
}
