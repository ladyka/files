package by.ladyka.files.service;

import by.ladyka.files.config.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalFilesServiceImpl implements FilesService {

    private final AppConfig appConfig;

    @SneakyThrows
    @Override
    public List<String> save(HttpServletRequest request) {
        LocalDate localDate = LocalDate.now();
        Path uploadFolder = Path.of(appConfig.getTarget(),
                String.valueOf(localDate.getYear()),
                String.valueOf(localDate.getMonthValue()),
                String.valueOf(localDate.getDayOfMonth()));
        Files.createDirectories(uploadFolder);

        List<String> filenames = new LinkedList<>();
        // Parse the request
        FileItemIterator iter = new ServletFileUpload().getItemIterator(request);
        while (iter.hasNext()) {
            FileItemStream item = iter.next();
            try (InputStream stream = item.openStream())  {
                if (!item.isFormField()) {
                    String filename = item.getName();
                    // Process the input stream
                    String outFileName = uploadFolder.toString() + UUID.randomUUID() + filename;
                    try (OutputStream out = new FileOutputStream(outFileName)) {
                        IOUtils.copy(stream, out);
                    }
                    outFileName = appConfig.getSite() + outFileName.substring(appConfig.getTarget().length());
                    filenames.add(outFileName);
                }
            }
        }
        return filenames;
    }
}
