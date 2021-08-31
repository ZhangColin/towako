package com.towako.system.common;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author colin
 */
public class TencentCOS {
    public static final String bucketName = "medical-record-1300983988";

    public static final String secretId = "AKIDLbgsjNrGlmbXFjKSilbEfs6NSLHxuccP";

    public static final String secretKey = "vTFduoW45EsrYIuWDjTCiZEmdCfw7221";

    private static COSCredentials credentials = new BasicCOSCredentials(secretId, secretKey);

    private static final String regionName = "ap-shanghai";
    private static ClientConfig clientConfig = new ClientConfig(new Region(regionName));

    public static String uploadfile(MultipartFile file, String key) throws IOException {
        COSClient cosClient = new COSClient(credentials, clientConfig);

        String fileName = file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentEncoding("UTF-8");

        if (".png".equals(fileName.substring(fileName.lastIndexOf(".")))) {
            metadata.setContentType("image/png");
        } else if (".jpg".equals(fileName.substring(fileName.lastIndexOf(".")))) {
            metadata.setContentType("image/jpeg");
        }

        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file.getInputStream(), metadata);
        putObjectRequest.setStorageClass(StorageClass.Standard);

        final PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        cosClient.shutdown();

        return "https://"+bucketName+".cos."+regionName+".myqcloud.com"+key;
    }

    public static String uploadfile(File file, String key) throws IOException {
        COSClient cosClient = new COSClient(credentials, clientConfig);

        String fileName = file.getName();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.length());
        metadata.setContentEncoding("UTF-8");

        if (".png".equals(fileName.substring(fileName.lastIndexOf(".")))) {
            metadata.setContentType("image/png");
        } else if (".jpg".equals(fileName.substring(fileName.lastIndexOf(".")))) {
            metadata.setContentType("image/jpeg");
        }

        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        putObjectRequest.setStorageClass(StorageClass.Standard);

        final PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        cosClient.shutdown();

        return "https://"+bucketName+".cos."+regionName+".myqcloud.com"+key;
    }

    public static void downFile() {
        final COSClient cosClient = new COSClient(credentials, clientConfig);
        String key = "down/demo1.jpg";
        final File downFile = new File("src/test/resources/mydown.txt");

        final GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        final ObjectMetadata objectMetadata = cosClient.getObject(getObjectRequest, downFile);
    }

    public static void deleteFile(String key) {
        final COSClient cosClient = new COSClient(credentials, clientConfig);
        cosClient.deleteObject(bucketName, key);
        cosClient.shutdown();
    }
}
