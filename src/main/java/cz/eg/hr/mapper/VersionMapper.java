package cz.eg.hr.mapper;

import cz.eg.hr.dtos.VersionRequestDto;
import cz.eg.hr.entities.Version;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VersionMapper {
    @Mapping(target = "javascriptFramework", ignore = true)
    Version dtoToEntity(VersionRequestDto versionRequestDto);

    default List<Version> dtosToEntities(List<VersionRequestDto> versionRequestDtos) {
        if (versionRequestDtos == null) {
            return null;
        }

        return versionRequestDtos.stream()
            .map(this::dtoToEntity)
            .collect(Collectors.toList());
    }
}
