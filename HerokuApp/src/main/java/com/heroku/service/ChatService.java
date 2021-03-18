package com.heroku.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.heroku.domain.Message;
import com.heroku.domain.User;

@Service
public class ChatService {

	/*
	 * public CollectionReference getUserCollection(){ return
	 * firestore.collection("users"); }
	 */

	public String saveMsg(Message msg) throws InterruptedException, ExecutionException {
		Firestore firestore = FirestoreClient.getFirestore();

		String chatId = setOneToOneChat(msg.getSenderid(), msg.getReceiverId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(msg.getId().toString(), msg);
		if (getChatCheck(chatId) != null) {
			ApiFuture<WriteResult> collectionApiFuture = firestore.collection("messages").document(chatId).update(map);
			return collectionApiFuture.get().getUpdateTime().toString();
		} else {
			ApiFuture<WriteResult> collectionApiFuture = firestore.collection("messages").document(chatId).set(map);
			return collectionApiFuture.get().getUpdateTime().toString();
		}

	}

	public Message getChatCheck(String test) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection("messages").document(test);
		ApiFuture<DocumentSnapshot> future = documentReference.get();

		DocumentSnapshot document = future.get();
		Message message = null;

		if (document.exists()) {
			message = document.toObject(Message.class);
			return message;
		} else {
			return null;
		}
	}

	public Map<String, Object> getAllChatsOfUser(Long id) throws InterruptedException, ExecutionException {
		Firestore firestore = FirestoreClient.getFirestore();
		Map<String, Object> map = new HashMap<String, Object>();
		ApiFuture<QuerySnapshot> query = firestore.collection("messages").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		ArrayList<String> ids = new ArrayList<>();
		List<String> splitStrings = new ArrayList<>();
		for (QueryDocumentSnapshot document : documents) {
			splitStrings.addAll(splitKeys(document.getId()));
			if (splitStrings.contains(id.toString())) {
				map.put(document.getId(), document.getData());
				ids.add(document.getId());
			}
			splitStrings.clear();
		}
		return map;
	}

	/**************************** Set One TO One Chat Key *******************/
	private String setOneToOneChat(String uid1, String uid2) {
		// Check if user1’s id is less than user2's
		int i = uid1.compareToIgnoreCase(uid2);
		if (i < 0) {
			return uid1 + "&" + uid2;
		} else {
			return uid2 + "&" + uid1;
		}

	}

	/************* SPlitting Keys Of Chat *******/
	private List<String> splitKeys(String uid) {

		String str = uid;
		List<String> strList = Arrays.asList(str.split("&"));
		return strList;
	}

	public Map<String, Object> getContactUserChats(Long userId, Long contactUserId)
			throws InterruptedException, ExecutionException {
		String resultChatkey = setOneToOneChat(userId.toString(), contactUserId.toString());
		Firestore firestore = FirestoreClient.getFirestore();
		Map<String, Object> map = new HashMap<String, Object>();
		DocumentReference documentReference = firestore.collection("messages").document(resultChatkey);
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		DocumentSnapshot document = future.get();
		map.put(resultChatkey, document.getData());
		return map;
	}

	public boolean deleteMsgById(String msgKey, String msgId) {
		Firestore firestore = FirestoreClient.getFirestore();

		try {
			Map<String, Object> deleteSong = new HashMap<>();
			deleteSong.put(msgKey, FieldValue.delete());

			firestore.collection("messages").document(msgId).update(deleteSong);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean deleteAllContactMsgs(String msgId) {
		Firestore firestore = FirestoreClient.getFirestore();
		try {
			ApiFuture<WriteResult> documentReference = firestore.collection("messages").document(msgId).delete();
		
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
