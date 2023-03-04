package ru.kampaii.application;

import feign.form.FormData;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kampaii.application.client.ApplicationEnhancedClient;
import ru.kampaii.application.client.ApplicationFormClient;
import ru.kampaii.example.model.TestDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Slf4j
@ActiveProfiles("test")
public class ApplicationControllerIntTest {

    @Autowired
    private ApplicationFormClient applicationFormClient;
    @Autowired
    private ApplicationEnhancedClient applicationEnhancedClient;

    Resource file = new ClassPathResource("test-file.pdf");

    private static final TestDto TEST_DTO = TestDto.builder().test("test").build();

    @Test
    @SneakyThrows
    void testFormSend() {
        int i = applicationFormClient.testStreamUpload(TEST_DTO,
                new FormData(file.getFilename(), MediaType.APPLICATION_PDF_VALUE, file.getInputStream().readAllBytes()));
        log.info("result is {}", i);
        assertEquals(1, i);
    }

    @Test
    @SneakyThrows
    void testMultipartSend() {
        var result = applicationFormClient.testFileUpload(TEST_DTO,
                new TestMultipartFile(file));

        assertEquals(1, result);
    }
}
