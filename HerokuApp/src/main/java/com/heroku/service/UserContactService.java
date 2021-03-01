package com.heroku.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.core.operation.Merge;
import com.heroku.domain.Message;
import com.heroku.domain.UserContacts;

@Service
public class UserContactService {
	
	public String saveUserContacts(UserContacts list, String userId) throws InterruptedException, ExecutionException {
		Map<String, Object> l=new HashMap<String, Object>();
		l.put(userId, list);
		Firestore firestore = FirestoreClient.getFirestore();
		
		CollectionReference collectionApiFuture = firestore.collection("contacts").
				document(userId).collection(list.getContactsId().get(0));
				collectionApiFuture.add(l);
		return collectionApiFuture.getId().toString();
		

	}

}
