package cz.eg.hr.controllers;

import cz.eg.hr.dtos.JavascriptFrameworkRequestDto;
import cz.eg.hr.dtos.JavascriptFrameworkResponseDto;
import cz.eg.hr.servicesImpl.JavascriptFrameworkServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/frameworks")
@RequiredArgsConstructor
public class JavascriptFrameworkController {

    private final JavascriptFrameworkServiceImpl javascriptFrameworkService;

    /**
     * Return all JavascriptFrameworks method
     *
     * @return ResponseEntity with Iterable of JavascriptFrameworkResponseDto
     */
    @GetMapping()
    public ResponseEntity<Iterable<JavascriptFrameworkResponseDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(javascriptFrameworkService.getAll());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<JavascriptFrameworkResponseDto>> filter(
        @RequestParam(required = false) String nameContains,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate,
        @RequestParam(required = false) Integer minRating,
        @RequestParam(required = false) Integer maxRating) {
        return ResponseEntity.status(HttpStatus.OK).body(javascriptFrameworkService.filter(nameContains, startDate, endDate, minRating, maxRating));
    }


    /**
     * Create javascriptFramework method
     *
     * @param javascriptFrameworkRequestDto required body to create entity
     * @return ResponseEntity if success otherwise return BAD_REQUEST if name in request is already in database
     */
    @PostMapping()
    public ResponseEntity<JavascriptFrameworkResponseDto> create(@Valid @Validated @RequestBody JavascriptFrameworkRequestDto javascriptFrameworkRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(javascriptFrameworkService.create(javascriptFrameworkRequestDto));
    }

    /**
     * Update javascriptFramework method
     *
     * @param id                            as javascriptFramework identification
     * @param javascriptFrameworkRequestDto holds updated data a
     * @return OK if success otherwise return NOT_FOUND or BAD_REQUEST
     */
    @PutMapping("/{id}")
    public ResponseEntity<JavascriptFrameworkResponseDto> edit(@PathVariable Long id,
                                                               @Valid @Validated @RequestBody JavascriptFrameworkRequestDto javascriptFrameworkRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(javascriptFrameworkService.update(id, javascriptFrameworkRequestDto));
    }

    /**
     * Delete javascriptFramework by provided id method
     *
     * @param id as path variable for requested javascript framework to be deleted
     * @return void with 204 no content if success otherwise return NOT_FOUND
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        javascriptFrameworkService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
