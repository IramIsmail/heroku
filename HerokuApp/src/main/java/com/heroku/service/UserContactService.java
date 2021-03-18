package com.heroku.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.heroku.domain.User;
import com.heroku.domain.UserContacts;

@Service
public class UserContactService {
	
	/*************************Add Contact*********************************/

	public String saveUserContacts(UserContacts contact, String userId)
			throws InterruptedException, ExecutionException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(contact.getContactsId(), userId);
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> collectionApiFuture = firestore.collection("contacts").document(userId).update(map);
		return collectionApiFuture.get().getUpdateTime().toString();
	}
	
	/*************************Getting List Of Contacts**************************/
	
	public List<User> getContactList(String userId) throws InterruptedException, ExecutionException {	
		Firestore firestore = FirestoreClient.getFirestore();		
		DocumentReference docRef = firestore.collection("contacts").document(userId);
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		Map<String, Object> map = document.getData();	
		List<User> users=new ArrayList<>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			User user=new User();
			DocumentReference docref2=firestore.collection("users").document(entry.getKey());
			ApiFuture<DocumentSnapshot> future2 = docref2.get();
			DocumentSnapshot document2 = future2.get();
			if(document2.exists()){
			user = document2.toObject(User.class);
			users.add(user);
			}
		}	
		return users;
	}

	/*********************Checking For Already In Contact List********************************/
	public boolean alreadyInContactList(String userId, String contactId)
			throws InterruptedException, ExecutionException {
		boolean alreadyInList = false;
		Firestore firestore = FirestoreClient.getFirestore();
		DocumentReference docRef = firestore.collection("contacts").document(userId);
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		if (document.get(contactId) != null) {
			alreadyInList = true;
		}
		return alreadyInList;
	}


/*****************************Delete particular Contact in a contact List*******************************/
	public boolean delete(UserContacts userContact) {
		Map<String, Object> updates = new HashMap<>();		
		Firestore firestore = FirestoreClient.getFirestore();
		DocumentReference docRef = firestore.collection("contacts").document(userContact.getId().toString());
		updates.put(userContact.getContactsId(), FieldValue.delete());
		ApiFuture<WriteResult> writeResult = docRef.update(updates);
		try {
			System.out.println("Update time : " + writeResult.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return true;
	}




}
