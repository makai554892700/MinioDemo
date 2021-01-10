package www.mys.com.miniodemo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import www.mys.com.utils.Result;

public interface FileApi {

    @PostMapping(value = "/upload/file")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file);

    @GetMapping(value = "/download/{fileId}")
    public void downloadFile(@PathVariable(value = "fileId") String fileId);

}
