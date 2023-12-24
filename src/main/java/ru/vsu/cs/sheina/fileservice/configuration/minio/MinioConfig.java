package ru.vsu.cs.sheina.fileservice.configuration.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.host}")
    private String minioHost;

    @Value("${minio.user}")
    private String user;

    @Value("${minio.password}")
    private String password;

    @Bean
    public MinioClient minioClient() throws Exception {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioHost)
                .credentials(user, password)
                .build();

        createBucket(minioClient);

        return minioClient;
    }

    private void createBucket(MinioClient minioClient) throws Exception {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(MinioBucket.PICTURE.toString()).build())) {
            minioClient.makeBucket(MakeBucketArgs
                    .builder()
                    .bucket(MinioBucket.PICTURE.toString())
                    .build());
        }

        String policy = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Action\":[\"s3:GetObject\"],\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Resource\":[\"arn:aws:s3:::" + MinioBucket.PICTURE.toString() + "/*\"],\"Sid\":\"\"}]}";

        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                .bucket(MinioBucket.PICTURE.toString())
                .config(policy)
                .build());
    }
}
