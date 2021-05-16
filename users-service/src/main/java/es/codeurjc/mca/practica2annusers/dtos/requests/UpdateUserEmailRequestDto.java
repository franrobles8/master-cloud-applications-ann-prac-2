package es.codeurjc.mca.practica2annusers.dtos.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UpdateUserEmailRequestDto {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email")
    private String email;

}
