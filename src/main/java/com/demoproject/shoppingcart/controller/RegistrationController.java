package com.demoproject.shoppingcart.controller;

import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demoproject.shoppingcart.model.User;
import com.demoproject.shoppingcart.service.RegistrationService;

@RestController
public class RegistrationController {

	@Autowired
	private RegistrationService service;
	
	public String encrypt(String str){
//       StringBuffer sb = new StringBuffer();
//      char ch[] = str.toCharArray();
//      for(int i = 0; i < ch.length; i++) {
//         String hexString = Integer.toHexString(ch[i]);
//         sb.append(hexString);
//      }
//      String result = sb.toString();
//      return result;
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(str.getBytes());
    }

	@PostMapping("/registeruser")
	@CrossOrigin(origins = "http://localhost:4200")
	public User registerUser(@RequestBody User user) throws Exception {
		String tempEmailId = user.getEmailId();
		if (tempEmailId != null && !"".equals(tempEmailId)) {
			User obj = service.fetchUserByEmailId(tempEmailId);
			if (obj != null)
				throw new Exception("User with " + tempEmailId + " already exists!");
		}
		User obj = null;
		String temppass=this.encrypt(user.getPassword());
		user.setPassword(temppass);
		obj = service.saveUser(user);
		return obj;
	}

	@PostMapping("/login")
	@CrossOrigin(origins = "http://localhost:4200")
	public User loginUser(@RequestBody User user) throws Exception {
		String tempEmailId = user.getEmailId();
		String tempPass = this.encrypt(user.getPassword());
		User userObj = null;
		if (tempEmailId != null && tempPass != null) {
			userObj = service.fetchUserByEmailIdAndPassword(tempEmailId, tempPass);
		}
		if (userObj == null)
			throw new Exception("Bad Credentials");
		return userObj;
	}
	
	@PostMapping("/updateuser")
	@CrossOrigin(origins = "http://localhost:4200")
	public User updateUser(@RequestBody User user) throws Exception {
		String tempass=this.encrypt(user.getPassword());
		user.setPassword(tempass);
		return service.saveUser(user);
	}

}
