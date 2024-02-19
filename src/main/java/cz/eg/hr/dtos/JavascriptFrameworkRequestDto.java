package cz.eg.hr.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

public record JavascriptFrameworkRequestDto(
    Long id,
    @NotBlank(message = "Name cannot be empty") String name,
    List<VersionRequestDto> versions,
    LocalDate lastSupportedDate,
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5") Integer rating
) {
}

