package www.mys.com.miniodemo.utils;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MinioUtils {

    private static final Logger log = LoggerFactory.getLogger(MinioUtils.class.getName());

    public static boolean uploadFile(String host, String accessKey, String secretKey, String bucketName
            , MultipartFile file) {
        log.info("uploadFile host=" + host + ";accessKey=" + accessKey + ";secretKey=" + secretKey
                + ";bucketName=" + bucketName);
        try {
            MinioClient minioClient = new MinioClient(host, accessKey, secretKey);
            boolean isExist = minioClient.bucketExists(bucketName);
            if (!isExist) {
                minioClient.makeBucket(bucketName);
            }
            minioClient.putObject(bucketName, file.getOriginalFilename(), file.getInputStream()
                    , file.getContentType());
            return true;
        } catch (Exception e) {
            log.info("uploadFile error: " + e);
            return false;
        }
    }

    public static boolean downloadFile(HttpServletRequest request, HttpServletResponse response, String host
            , String accessKey, String secretKey, String bucketName, String objectName) {
        log.info("downloadFile host=" + host + ";accessKey=" + accessKey + ";secretKey=" + secretKey
                + ";bucketName=" + bucketName + ";objectName=" + objectName);
        try {
            MinioClient minioClient = new MinioClient(host, accessKey, secretKey);
            boolean isExist = minioClient.bucketExists(bucketName);
            if (!isExist) {
                log.info("bucketName not exist:bucketName=" + bucketName);
                return false;
            }
            ObjectStat objectStat = minioClient.statObject(bucketName, objectName);
            return DownloadUtils.downloadFile(request, response, minioClient.getObject(bucketName, objectName)
                    , objectStat.name(), objectStat.length(), objectStat.contentType(), true, -1);
        } catch (Exception e) {
            log.info("downloadFile error: " + e);
        }
        return false;
    }

}
