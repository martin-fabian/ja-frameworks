package cz.eg.hr.dtos;

import javax.validation.constraints.NotBlank;

public record VersionRequestDto(
    Long id,
    @NotBlank(message = "Name cannot be empty") String number
) {
}

