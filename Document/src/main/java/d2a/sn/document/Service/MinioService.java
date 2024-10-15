package d2a.sn.document.Service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class MinioService {
    private final MinioClient minioClient;
    @Value("${minio.bucket.name}")
    private String minioUrl;
    public MinioService(MinioClient minioClient){
        this.minioClient=minioClient;

    }
    public String uploadFile(String fileName, InputStream stream, String contentType) throws Exception {
        minioClient.putObject(PutObjectArgs.builder()
                            .bucket("projetstage2024")
                            .object(fileName)
                            .stream(stream, stream.available(), -1)
                            .contentType(contentType)
                            .build()
            );
        String fileUrl = String.format("%s/%s/%/s",
                minioUrl,
                "projetstage2024",
                fileName);

        return fileUrl;
    }
}
