package www.mys.com.miniodemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import www.mys.com.miniodemo.api.FileApi;
import www.mys.com.miniodemo.utils.MinioUtils;
import www.mys.com.utils.Result;
import www.mys.com.utils.ResultUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class FileController implements FileApi {

    @Value(value = "${minio.host:http://192.168.3.6:9000}")
    private String minioHost;
    @Value(value = "${minio.accessKey:root}")
    private String minioAccessKey;
    @Value(value = "${minio.secretKey:marking123}")
    private String minioSecretKey;
    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;

    @Override
    public Result<String> uploadFile(MultipartFile file) {
        if (MinioUtils.uploadFile(minioHost, minioAccessKey, minioSecretKey, "test", file)) {
            return ResultUtils.succeed("success.");
        } else {
            return ResultUtils.field("field.");
        }
    }

    @Override
    public void downloadFile(String fileId) {
        MinioUtils.downloadFile(request, response, minioHost, minioAccessKey
                , minioSecretKey, "test", fileId);
    }
}
