package com.heroku.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.heroku.domain.User;

@Service
public class UserService {
	public String save(User user) throws InterruptedException, ExecutionException {
		Firestore firestore = FirestoreClient.getFirestore();

		ApiFuture<WriteResult> collectionApiFuture = firestore.collection("users").document(user.getId().toString())
				.set(user);
		return collectionApiFuture.get().getUpdateTime().toString();
	}

	public void updatedeviceToken(String id, String deviceToken) throws InterruptedException, ExecutionException {
		Firestore firestore = FirestoreClient.getFirestore();
		User user = getUser(id);
		if(user!=null){
		user.setDeviceToken(deviceToken);
		ApiFuture<WriteResult> collectionApiFuture = firestore.collection("users").document(id).set(user);
		collectionApiFuture.get().getUpdateTime().toString();
		}

	}

	public User getUser(String id) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection("users").document(id);
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		DocumentSnapshot document = future.get();
		User user = null;
		if (document.exists()) {
			user = document.toObject(User.class);
			return user;
		} else {
			return null;
		}
	}

	
	
	public void logout(String userId) {
		Map<String, Object> map=new HashMap<>();
		map.put("deviceToken", null);
		Firestore firestore=FirestoreClient.getFirestore();
		DocumentReference drefrence =
				firestore.collection("users").document(userId);
		drefrence.update(map);
		
		
	}

	public boolean update(User user) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();		
		DocumentReference documentReference = dbFirestore.collection("users").document(user.getId().toString());
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		DocumentSnapshot document = future.get();		
		if(document.exists()){
			ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("users").document(user.getId().toString()).set(user);
			collectionApiFuture.get().getUpdateTime().toString();
		return true;
		}
		else{
			return false;
		}
	}

}
