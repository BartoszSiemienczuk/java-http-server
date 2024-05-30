package info.jbsoftware.controllers;

import info.jbsoftware.httpserver.common.utils.FileUtils;
import info.jbsoftware.httpserver.request.model.HttpRequest;
import info.jbsoftware.httpserver.response.HttpResponseFactory;
import info.jbsoftware.httpserver.response.model.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FilesController {
    public HttpResponse serveFiles(final HttpRequest request) {
        final String path = request.path();
        final String filename = path.substring(path.lastIndexOf('/') + 1);
        final String filePath = FileUtils.FILE_PATH + filename;
        final File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            return HttpResponseFactory.notFound();
        }

        try {
            final byte[] fileBytes = Files.readAllBytes(file.toPath());
            return HttpResponseFactory.okBytes(fileBytes, "application/octet-stream");
        } catch (IOException e) {
            final String message = String.format("Error reading file [%s]: %s", filename, e.getMessage());
            return HttpResponseFactory.error(message);
        }

    }
}
