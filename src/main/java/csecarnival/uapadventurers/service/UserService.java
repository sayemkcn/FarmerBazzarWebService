package csecarnival.uapadventurers.service;

import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import csecarnival.uapadventurers.dto.User;
import csecarnival.uapadventurers.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public User saveUser(User user) {
		userRepository.saveAndFlush(user);
		return user;
	}

	// check if a file is image
	public boolean isImageValid(MultipartFile multipartFile) throws EOFException {
		try {
			Image image = ImageIO.read(this.convertToFile(multipartFile));
			if (image != null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	// convert Multipart to File
	private File convertToFile(MultipartFile multipartFile) throws Exception {
		File file = new File(multipartFile.getOriginalFilename());
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(multipartFile.getBytes());
		fos.close();
		return file;
	}

	// upload image to server.
	public String uploadToletImage(MultipartFile multipartFile, String phoneNumber) throws Exception {
		byte[] bytes = multipartFile.getBytes();

		String rootPath = System.getProperty("catalina.home");
		File directory = new File(
				rootPath + File.separator + "AppData" + File.separator + "farmerbazzar" + File.separator + phoneNumber);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		File imageFile = File.createTempFile("upload_", ".png", directory);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(imageFile));
		bufferedOutputStream.write(bytes);
		bufferedOutputStream.close();
		return File.separator + "AppData" + File.separator + "BdTolet" + File.separator + phoneNumber + File.separator
				+ imageFile.getName();
	}

	public boolean isAccountExist(String phoneOrEmail) {
		if (userRepository.findByEmail(phoneOrEmail) != null
				|| userRepository.findByPhoneNumber(phoneOrEmail) != null) {
			return true;
		}
		return false;
	}

	public User findUserByEmailOrPhone(String phoneOrEmail) {
		if (userRepository.findByEmail(phoneOrEmail) != null) {
			return userRepository.findByEmail(phoneOrEmail);
		} else if (userRepository.findByPhoneNumber(phoneOrEmail) != null) {
			return userRepository.findByPhoneNumber(phoneOrEmail);
		}
		return null;
	}

	public User findUserByToken(String token) {
		User user = userRepository.findByToken(token);
		return user;
	}

	public User findUserById(Long id) {
		return userRepository.findById(id);
	}

}
