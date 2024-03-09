package cz.eg.hr.repositories;

import cz.eg.hr.entities.JavascriptFramework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JavascriptFrameworkRepository extends JpaRepository<JavascriptFramework, Long> {
    Optional<JavascriptFramework> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT j FROM JavascriptFramework j " +
        "WHERE (:nameContains IS NULL OR j.name LIKE CONCAT('%', :nameContains, '%')) " +
        "AND (:startDate IS NULL OR j.lastSupportedDate >= :startDate) " +
        "AND (:endDate IS NULL OR j.lastSupportedDate <= :endDate) " +
        "AND (:minRating IS NULL OR j.rating >= :minRating) " +
        "AND (:maxRating IS NULL OR j.rating <= :maxRating)")
    List<JavascriptFramework> filter(
        @Param("nameContains") String nameContains,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("minRating") Integer minRating,
        @Param("maxRating") Integer maxRating);
}
