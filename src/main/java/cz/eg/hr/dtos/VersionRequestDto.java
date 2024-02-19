package cz.eg.hr.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersionRequestDto {
    private Long id;

    @NotBlank
    private String number;
}
