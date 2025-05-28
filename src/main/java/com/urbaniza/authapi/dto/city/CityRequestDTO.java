package com.urbaniza.authapi.dto.city;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CityRequestDTO(

    @NotBlank(message = "O nome da cidade não pode ser vazio ou nulo.")
    @Size(min = 2, max = 100, message = "O nome da cidade deve ter entre 2 e 100 caracteres.")
    String name,

    @NotBlank(message = "A UF não pode ser vazia ou nula.")
    @Size(min = 2, max = 2, message = "A UF deve ter exatamente 2 caracteres.")
    @Pattern(regexp = "[A-Z]{2}", message = "A UF deve conter exatamente 2 letras maiúsculas.")
    String uf

) {}