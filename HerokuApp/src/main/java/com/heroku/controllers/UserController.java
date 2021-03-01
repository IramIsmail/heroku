package com.heroku.controllers;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heroku.domain.JwtResponse;
import com.heroku.domain.User;
import com.heroku.service.UserService;
import com.heroku.utils.JwtTokenUtil;

@RestController
@RequestMapping("/user/")
public class UserController {
	/*@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;
	*/
	@Autowired
	private UserService userService;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	/*****
	 * ---------------------------Save User-----------------------
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody User user, HttpServletRequest req) {
		try {
			Random random = new Random(System.nanoTime());
			int r = (int) (Math.random() * Long.MAX_VALUE);
			long randomInt = (long) random.nextInt(r);
			user.setId(randomInt);
			userService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
/*****
 * ****************************Login User with device code
 * @param user
 * @param req
 * @return
 * @throws InterruptedException
 * @throws ExecutionException
 */

	@RequestMapping(value="authentication",method = RequestMethod.POST)
	public ResponseEntity<?> authUser(@RequestBody User user, HttpServletRequest req) throws InterruptedException, ExecutionException {
	/*try {
		authenticate(user.getName(),"abc");
	} catch (Exception e) {
		e.printStackTrace();
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}*/
	JwtResponse response = getResponse(user.getId().toString(),
			user.getDeviceToken());
	return ResponseEntity.ok(response);

	}
	

	public JwtResponse getResponse(String id, String deviceToken) throws InterruptedException, ExecutionException {
	//	final UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(id);

		if (deviceToken != null)
			userService.updatedeviceToken(id, deviceToken);

		final String token = jwtTokenUtil.generateToken(id);
		JwtResponse response = new JwtResponse(token,"user" );
		return response;
	}
	
	/*private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}*/
	
	
	/*************************Get User***********/
	@RequestMapping(value="{userId}",method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable Long userId) throws InterruptedException, ExecutionException {
		
		
		User user= userService.getUser(userId.toString());


		return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	
/******************************Log out User Code**************************/
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<?> logout(@RequestParam("userId") String userId) {
		if (!userId.equals(null)) {
			userService.logout(userId);
			return ResponseEntity.ok(null);
		} else {
			return (ResponseEntity<?>) ResponseEntity.badRequest();
		}

	}
}
