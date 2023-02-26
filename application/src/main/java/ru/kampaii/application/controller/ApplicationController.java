package ru.kampaii.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.kampaii.example.model.TestDto;

@RestController
@Slf4j
public class ApplicationController {

    @PostMapping(path = "/file")
    public void testFileUpload(
            @RequestPart("dto") TestDto testDto,
            @RequestPart("file") MultipartFile file
    ) {
        log.info("incoming {}, filesize {}", testDto, file.getSize());
    }

    @PostMapping(path = "/stream")
    public void testStreamUpload(
            @RequestPart("dto") TestDto testDto,
            @RequestPart("file") byte[] file
    ) {
        log.info("incoming {} bytes {}", testDto, file);
    }
}
