package by.ladyka.files.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FilesService {
    List<String> save(HttpServletRequest request);
}
