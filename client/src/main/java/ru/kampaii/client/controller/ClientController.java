package ru.kampaii.client.controller;

import feign.Param;
import feign.form.FormData;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.kampaii.client.client.ApplicationEnhancedClient;
import ru.kampaii.client.client.ApplicationFormClient;
import ru.kampaii.example.model.TestDto;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ApplicationEnhancedClient applicationEnhancedClient;
    private final ApplicationFormClient applicationFormClient;

    @SneakyThrows
    @PostMapping(path = "/form/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public int testPostForm(
            @RequestPart("dto") TestDto testDto,
            @RequestPart("file") MultipartFile file
    ) {
        log.info("sending {} bytes {}", testDto, file.getSize());
        return applicationFormClient.testFileUpload(testDto, file);
    }

    @SneakyThrows
    @PostMapping(path = "/form/stream")
    public int testStreamUploadForm(
            @RequestPart("dto") TestDto testDto,
            @RequestPart("file") MultipartFile file
    ) {
        log.info("formData sending {} bytes {}", testDto, file.getSize());
        return applicationFormClient.testStreamUpload(testDto, new FormData(file.getContentType(), file.getName(), file.getBytes()));
    }

    @SneakyThrows
    @PostMapping(path = "/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public int testPost(
            @RequestPart("dto") TestDto testDto,
            @RequestPart("file") MultipartFile file
    ) {
        log.info("[ENHANCED] sending {} bytes {}", testDto, file.getSize());
        return applicationEnhancedClient.testFileUpload(testDto, file);
    }

    @SneakyThrows
    @PostMapping(path = "/stream")
    public int testStreamUpload(
            @RequestPart("dto") TestDto testDto,
            @RequestPart("file") MultipartFile file
    ) {
        log.info("[ENHANCED] formData sending {} bytes {}", testDto, file.getSize());
        return applicationEnhancedClient.testStreamUpload(testDto, new FormData(file.getContentType(), file.getName(), file.getBytes()));
    }
}
