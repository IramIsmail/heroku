package com.heroku.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

@Service
public class FirebaseService {
	 @PostConstruct
	    public void initialize() throws Exception {
	            InputStream serviceAccount =this.getClass().getClassLoader().getResourceAsStream("./notifycheck-dfc1f-firebase-adminsdk-ca4t0-b33b5e3d92.json");
	        	
				FirebaseOptions options = FirebaseOptions.builder()
						.setCredentials(GoogleCredentials.fromStream(serviceAccount))
	                    .setDatabaseUrl("https://notifycheck-dfc1f.firebaseio.com").build();
	        if(FirebaseApp.getApps().isEmpty()){
	            FirebaseApp.initializeApp(options);
	        }
	        	
	    }
	 public Firestore getFirebase(){
     	return FirestoreClient.getFirestore();
     }
}



