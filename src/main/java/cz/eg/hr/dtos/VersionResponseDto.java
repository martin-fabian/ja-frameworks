package cz.eg.hr.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersionResponseDto {
    @NonNull
    private Long id;

    @NotBlank
    private String number;
}
