package ru.kampaii.application;

import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestMultipartFile implements MultipartFile {

    private final Resource resource;

    public TestMultipartFile(Resource resource) {
        this.resource = resource;
    }

    @Override
    public String getName() {
        return resource.getFilename();
    }

    @Override
    public String getOriginalFilename() {
        return resource.getFilename();
    }

    @Override
    public String getContentType() {
        return MediaType.APPLICATION_PDF_VALUE;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    @SneakyThrows
    public long getSize() {
        return resource.getFile().length();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return getInputStream().readAllBytes();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return resource.getInputStream();
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (FileOutputStream fos = new FileOutputStream(dest)) {
            fos.write(getBytes());
        }
    }
}
