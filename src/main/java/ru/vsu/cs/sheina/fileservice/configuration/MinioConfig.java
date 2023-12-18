package ru.vsu.cs.sheina.fileservice.configuration;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

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

        String policy = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::" + MinioBucket.PICTURE.toString() + "/*\"],\"Condition\":{\"StringEquals\":{\"s3:authType\":[\"Basic\"]}}}]}\n";

        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                .bucket(MinioBucket.PICTURE.toString())
                .config(policy)
                .build());
    }
}
