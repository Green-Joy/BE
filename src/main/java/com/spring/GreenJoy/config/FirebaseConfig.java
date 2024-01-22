package com.spring.GreenJoy.config;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

public class FirebaseConfig {

    @Value("#{app.firebase-configuration-file}")
    private String firebaseKey;

    @PostConstruct
    public void init() {

        try {
            FileInputStream serviceAccount = new FileInputStream(firebaseKey);
            FirebaseOptions.Builder optionBuilder = FirebaseOptions.builder();
            FirebaseOptions options = optionBuilder
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
