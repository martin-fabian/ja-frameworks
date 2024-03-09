package cz.eg.hr.controllers;

import cz.eg.hr.dtos.JavascriptFrameworkRequestDto;
import cz.eg.hr.dtos.JavascriptFrameworkResponseDto;
import cz.eg.hr.dtos.VersionRequestDto;
import cz.eg.hr.dtos.VersionResponseDto;
import cz.eg.hr.entities.JavascriptFramework;
import cz.eg.hr.entities.Version;
import cz.eg.hr.mapper.JavascriptFrameworkMapper;
import cz.eg.hr.mapper.VersionMapper;
import cz.eg.hr.repositories.JavascriptFrameworkRepository;
import cz.eg.hr.servicesImpl.JavascriptFrameworkServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class JavascriptFrameworkControllerTest {
    @Mock
    private JavascriptFrameworkRepository javascriptFrameworkRepository;

    @Mock
    private JavascriptFrameworkMapper javascriptFrameworkMapper;

    @Mock
    private VersionMapper versionMapper;

    @InjectMocks
    private JavascriptFrameworkServiceImpl javascriptFrameworkService;

    @Test
    void textCreateJavascriptFrameworkSuccessfully() {
        // Arrange
        List<VersionRequestDto> versionRequestDtoList = new ArrayList<>();
        List<VersionResponseDto> versionResponseDtos = new ArrayList<>();
        List<Version> versions = new ArrayList<>();
        JavascriptFrameworkRequestDto javascriptFrameworkRequestDto = new JavascriptFrameworkRequestDto("Angular", versionRequestDtoList, null, null);
        JavascriptFramework javascriptFrameworkResult = new JavascriptFramework(1L, javascriptFrameworkRequestDto.name(), versions, null, null);
        JavascriptFramework javascriptFrameworkEntity = new JavascriptFramework(null, javascriptFrameworkRequestDto.name(), versions, null, null);
        JavascriptFrameworkResponseDto javascriptFrameworkResponseDto = new JavascriptFrameworkResponseDto(javascriptFrameworkResult.getId(),
            javascriptFrameworkResult.getName(), versionResponseDtos, javascriptFrameworkResult.getLastSupportedDate(), javascriptFrameworkResult.getRating());

        when(javascriptFrameworkMapper.dtoToEntity(javascriptFrameworkRequestDto)).thenReturn(javascriptFrameworkEntity);
        when(javascriptFrameworkRepository.existsByName(javascriptFrameworkRequestDto.name())).thenReturn(false);
        when(javascriptFrameworkMapper.entityToDto(javascriptFrameworkResult)).thenReturn(javascriptFrameworkResponseDto);
        when(javascriptFrameworkRepository.save(javascriptFrameworkEntity)).thenReturn(javascriptFrameworkResult);

        // Action
        JavascriptFrameworkResponseDto javascriptFrameworkResponseDtoFinal = javascriptFrameworkService.create(javascriptFrameworkRequestDto);

        // Assert
        assertEquals("Error in assertion names, not equal", javascriptFrameworkResponseDtoFinal.getName(), javascriptFrameworkRequestDto.name());
    }

}
