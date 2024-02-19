package cz.eg.hr.mapper;

import cz.eg.hr.dtos.JavascriptFrameworkRequestDto;
import cz.eg.hr.dtos.JavascriptFrameworkResponseDto;
import cz.eg.hr.entities.JavascriptFramework;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JavascriptFrameworkMapper {
    JavascriptFrameworkResponseDto entityToDto(JavascriptFramework javascriptFramework);

    @Mapping(target = "versions", ignore = true)
    JavascriptFramework dtoToEntity(JavascriptFrameworkRequestDto javascriptFrameworkRequestDto);

}
