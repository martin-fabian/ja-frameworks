package cz.eg.hr.servicesImpl;

import cz.eg.hr.dtos.JavascriptFrameworkRequestDto;
import cz.eg.hr.dtos.JavascriptFrameworkResponseDto;
import cz.eg.hr.entities.JavascriptFramework;
import cz.eg.hr.entities.Version;
import cz.eg.hr.exceptions.BadRequestException;
import cz.eg.hr.exceptions.NotFoundException;
import cz.eg.hr.mapper.JavascriptFrameworkMapper;
import cz.eg.hr.mapper.VersionMapper;
import cz.eg.hr.repositories.JavascriptFrameworkRepository;
import cz.eg.hr.service.JavascriptFrameworkService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class JavascriptFrameworkServiceImpl implements JavascriptFrameworkService<JavascriptFrameworkResponseDto, JavascriptFrameworkRequestDto> {

    private final JavascriptFrameworkRepository javascriptFrameworkRepository;
    private final JavascriptFrameworkMapper javascriptFrameworkMapper;
    private final VersionMapper versionMapper;

    @Override
    public JavascriptFrameworkResponseDto create(JavascriptFrameworkRequestDto dto) {
        Optional<JavascriptFramework> existingFramework = javascriptFrameworkRepository.findByName(dto.name());
        existingFramework.ifPresent(framework -> {
            throw new BadRequestException(String.format("Framework with name %s already exists", dto.name()));
        });

        List<Version> versions = versionMapper.dtosToEntities(dto.versions());
        JavascriptFramework javascriptFramework = javascriptFrameworkMapper.dtoToEntity(dto);

        for (Version version : versions) {
            version.setJavascriptFramework(javascriptFramework);
        }
        javascriptFramework.setVersions(versions);

        return javascriptFrameworkMapper.entityToDto(javascriptFrameworkRepository.save(javascriptFramework));
    }

    @Override
    public JavascriptFrameworkResponseDto update(Long id, JavascriptFrameworkRequestDto dto) {
        Optional<JavascriptFramework> existingFramework = javascriptFrameworkRepository.findById(id);
        AtomicReference<JavascriptFrameworkResponseDto> javascriptFramework = new AtomicReference<>();
        existingFramework.ifPresentOrElse(frameworkEntity -> {
                if (!isNameBelongingToUpdatedObject(dto, id)) {
                    throw new BadRequestException(String.format("Framework with name %s already exists", dto.name()));
                }
                frameworkEntity.getVersions().clear();
                List<Version> versions = versionMapper.dtosToEntities(dto.versions());
                versions.forEach(version -> version.setJavascriptFramework(frameworkEntity));
                frameworkEntity.setVersions(versions);
                frameworkEntity.setRating(dto.rating());
                frameworkEntity.setName(dto.name());
                frameworkEntity.setLastSupportedDate(dto.lastSupportedDate());
                javascriptFramework.set(javascriptFrameworkMapper.entityToDto(javascriptFrameworkRepository.save(frameworkEntity)));
            },
            () -> {
                throw new NotFoundException(String.format("Framework with ID %s was not found", id));
            });
        return javascriptFramework.get();
    }

    private boolean isNameBelongingToUpdatedObject(JavascriptFrameworkRequestDto javascriptFrameworkRequestDto, Long id) {
        Optional<JavascriptFramework> existingJavascript = javascriptFrameworkRepository.findByName(javascriptFrameworkRequestDto.name());
        return existingJavascript.map(framework -> framework.getId().equals(id)).orElse(false);
    }

    @Override
    public void delete(Long id) {
        Optional<JavascriptFramework> existingFramework = javascriptFrameworkRepository.findById(id);
        existingFramework.ifPresentOrElse(framework -> {
                javascriptFrameworkRepository.deleteById(framework.getId());
            },
            () -> {
                throw new NotFoundException(String.format("Framework with ID %s was not found", id));
            });
    }

    @Override
    public List<JavascriptFrameworkResponseDto> getAll() {
        Iterable<JavascriptFramework> entities = javascriptFrameworkRepository.findAll();
        return mapEntitiesToDtos(entities);
    }

    public List<JavascriptFrameworkResponseDto> filter(String nameContains, LocalDate startDate, LocalDate endDate, Integer minRating, Integer maxRating) {
        return mapEntitiesToDtos(javascriptFrameworkRepository.filter(nameContains, startDate, endDate, minRating, maxRating));
    }

    public List<JavascriptFrameworkResponseDto> mapEntitiesToDtos(Iterable<JavascriptFramework> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
            .map(javascriptFrameworkMapper::entityToDto)
            .collect(Collectors.toList());
    }
}
