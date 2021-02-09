package com.heroku.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class FirebaseService {
	 @PostConstruct
	    public void initialize() throws IOException {
	            InputStream serviceAccount =this.getClass().getClassLoader().getResourceAsStream("./notifycheck-dfc1f-firebase-adminsdk-ca4t0-bf98b9dba7.json");	        	
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



