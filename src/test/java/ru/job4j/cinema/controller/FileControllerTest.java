package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.service.FileService;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

class FileControllerTest {

    private FileService fileService;
    private FileController fileController;

    @BeforeEach
    public void initFileController() {
        fileService = Mockito.mock(FileService.class);
        fileController = new FileController(fileService);
    }

    @Test
    public void whenGetById() {
        File file = new File(1, "test", "test");
        byte[] data = new byte[]{1, 2};
        FileDto fileDto = new FileDto(file.getName(), data);
        Mockito.when(fileService.findById(file.getId())).thenReturn(Optional.of(fileDto));
        ResponseEntity<?> response = fileController.getById(file.getId());
        Mockito.verify(fileService).findById(file.getId());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(data);
    }

    @Test
    public void whenGetByInvalidIdThenNotFound() {
        int invalidId = 0;
        Mockito.when(fileService.findById(invalidId)).thenReturn(Optional.empty());
        ResponseEntity<?> response = fileController.getById(invalidId);
        Mockito.verify(fileService).findById(invalidId);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo(null);
    }
}