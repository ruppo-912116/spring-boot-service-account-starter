package com.ruppo.serviceaccountdemo;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
@RestController
public class ServiceAccountDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAccountDemoApplication.class, args);
    }

    @GetMapping("/listBuckets")
    public String listBuckets() throws IOException {
        ClassPathResource res = new ClassPathResource("service_account.json");

        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(res.getFile()));

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();

        StringBuilder sb = new StringBuilder();
        for (Bucket bucket : storage.list().iterateAll()) {
            sb.append(bucket.getName()).append("\n");
        }
        return sb.toString();
    }

}
