package cz.eg.hr.service;

import java.time.LocalDate;
import java.util.List;

public interface JavascriptFrameworkService<R, D> {
    R create(D dto);

    R update(Long id, D dto);

    void delete(Long id);

    Iterable<R> getAll();

    List<R> filter(String nameContains, LocalDate startDate, LocalDate endDate, Integer minRating, Integer maxRating);
}
