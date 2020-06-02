package by.ladyka.files.www;

import by.ladyka.files.service.FilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
class IndexController {

    private final FilesService filesService;

    @RequestMapping(method = RequestMethod.POST, value = "upload")
    public @ResponseBody
    List<String> upload(HttpServletRequest request) {
        return filesService.save(request);
    }

}