package com.heroku.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
@Service
public class FirebaseService {

	   public boolean sendNotification(){
	try {
		
		 /** Resource resource = ; File initialFile = new
		 * File("/usr/google-services.json"); InputStream targetStream = new
		 * FileInputStream(initialFile);*/
		 
		FirebaseOptions options = FirebaseOptions.builder().setCredentials(
				GoogleCredentials.fromStream(new ClassPathResource("google-services.json").getInputStream()))
				.build();

		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		} else {
			FirebaseApp.getInstance();
		}
	} catch (IOException e) {

		System.err.println("Create FirebaseApp Error" + e.getMessage());
		return false;
	}

	Notification.Builder builder = Notification.builder();
	builder.setTitle("jjjj");
	builder.setBody("jdjd");
	Map<String, String> data = new HashMap<>();
	data.put("title", "jjjj");
	data.put("message", "jkjk");

	
	Notification notification = builder.build();

	try {
		MulticastMessage message = MulticastMessage.builder().setNotification(notification).putAllData(data)
				.addToken("e1tTqOWoRTCQydCW7qA6Mw:APA91bEHojZPFltoXHpuZ65vvzgl2Ctf_bct_5ineJGDqtNHM22B6AdtkDcVklaFGC5PRxNQMdQrK8pW1ELgvu9qKZO4asAr7xkEr12DTIZJEmxXjGMwrEtBUd5bY5dT0EyDAnxBknFH").build();
		BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
		// See the BatchResponse reference documentation
		// for the contents of response.
		System.out.println(response.getSuccessCount() + " messages were sent successfully");
		System.out.println("Notification sent");
		return true;
	} catch (Exception e) {
		e.printStackTrace();
		System.err.println("exception" + e.getMessage());
		return false;
	}

	   }
}



