package com.heroku.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.heroku.domain.Message;
import com.heroku.domain.User;
import com.heroku.service.ChatService;
import com.heroku.service.UserService;
import com.heroku.utils.FirebaseService;

@RestController
@RequestMapping("/chat/")
public class ChatController {

	@Autowired
	ChatService operationService;

	@Autowired
	UserService userService;

	@Autowired
	FirebaseService firebaseService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@RequestBody User user) {
		

		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
		/***
		 * ----------------Save Message
		 * @param message
		 * @param req
		 * @return
		 */
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> save_messages(@RequestBody Message message, HttpServletRequest req) {
		try {
			Random random = new Random(System.nanoTime());
			int r = (int) (Math.random() * Long.MAX_VALUE);
			long randomInt = (long) random.nextInt(r);
			message.setId(randomInt);
			message.setSenderid(message.getSenderid());
			message.setReceiverId(message.getReceiverId());
			operationService.saveMsg(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	
	/***
	 * -------------Getting All Messages of a particular User
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * 
	 */
	@RequestMapping(value="all/{userId}",method = RequestMethod.GET)
	public Map<String,Object> get_messages(@PathVariable Long userId , HttpServletRequest req) throws InterruptedException, ExecutionException {
			return  operationService.getAllChatsOfUser(userId);
	}
		/***
		 * Getting particular ContactUser Messages
		 * @return 
		 * @throws ExecutionException 
		 * @throws InterruptedException 
		 */
	
	@RequestMapping(value="{contactUserId}/{userId}",method = RequestMethod.GET)
	public Map<String, Object> getContactUserChats(@PathVariable Long userId,@PathVariable Long contactUserId) throws InterruptedException, ExecutionException{
		return operationService.getContactUserChats(userId,contactUserId);
		
	}

	
}
