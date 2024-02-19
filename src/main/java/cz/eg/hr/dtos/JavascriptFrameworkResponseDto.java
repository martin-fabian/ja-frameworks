package cz.eg.hr.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JavascriptFrameworkResponseDto {
    @NonNull
    private Long id;

    @NotBlank
    private String name;

    private List<VersionResponseDto> versions;

    private LocalDate lastSupportedDate;

    private Integer rating;
}
