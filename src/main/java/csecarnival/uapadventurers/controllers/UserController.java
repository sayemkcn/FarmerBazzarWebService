package csecarnival.uapadventurers.controllers;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import csecarnival.uapadventurers.common.SessionIdentifierGenerator;
import csecarnival.uapadventurers.dto.User;
import csecarnival.uapadventurers.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value="/",method=RequestMethod.GET)
	public String sayHello() {
		return "index";
	}

	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public User register(@ModelAttribute User user, BindingResult bindingResult,@RequestParam("userImage") MultipartFile multipartFile)
			throws EOFException {
		if (!bindingResult.hasErrors()) {
			// check if multipart file is empty or not
			if (!multipartFile.isEmpty()) {
				// check if multipart is valid
				if (userService.isImageValid(multipartFile)) {
					try {
						// save image and get path
						// set path to user object
						String imagePath = userService.uploadToletImage(multipartFile, user.getPhoneNumber());
						user.setImagePath(imagePath);
						SessionIdentifierGenerator sessionIdentifierGenerator = new SessionIdentifierGenerator();
						String token = sessionIdentifierGenerator.nextSessionId();
						user.setToken(token);
						
						userService.saveUser(user);
						
						return user;

					} catch (Exception e) {
						System.out.println(e.toString());
					}
				}

				userService.saveUser(user);
			}else {
				System.out.println("File can not be empty!");
			}
			return null;
		}else{
			return null;
		}

	}

	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam Map<String, String> map){
		String phoneOrEmail = map.get("emailOrPhoneNumber");
		String password = map.get("password");
		// check if user account exist
		if(userService.isAccountExist(phoneOrEmail)){
			User user = userService.findUserByEmailOrPhone(phoneOrEmail);
			if(password.equals(user.getPassword())){
				return "logged in. Token: "+user.getToken();
			}else{
				return "password wrong";
			}
		}else{
			return "account null";
		}
		
	}
	
	@RequestMapping(value="/user",method=RequestMethod.GET)
	@ResponseBody
	public User getUserByToken(@RequestParam("token") String token){
		User user = userService.findUserByToken(token);
		return user;
	}
	
	@RequestMapping(value="/image",method=RequestMethod.GET)
	public String showImage(@RequestParam("imagePath") String imagePath,Model model) {
		String rootPath = System.getProperty("catalina.home");
//		File file = new File(rootPath+File.separator+imagePath);
		model.addAttribute("imagePath",rootPath+File.separator+imagePath);
		return "showImage";
//		model.addAttribute("imagePath",rootPath+imagePath);
//		return rootPath+imagePath;
	}
}
