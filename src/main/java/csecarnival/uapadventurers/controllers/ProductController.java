package csecarnival.uapadventurers.controllers;

import java.io.EOFException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import csecarnival.uapadventurers.dto.Product;
import csecarnival.uapadventurers.service.ProductService;
import csecarnival.uapadventurers.service.UserService;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;

	@RequestMapping("/")
	@ResponseBody
	public String hello() {
		return "hello";
	}

	@RequestMapping(value = "/{id}/newProduct", method = RequestMethod.POST)
	@ResponseBody
	public Product uploadProduct(@ModelAttribute Product product, BindingResult bindingResult,
			@RequestParam("productImage") MultipartFile multipartFile, @PathVariable("id") Long id) throws EOFException {

		if (!bindingResult.hasErrors()) {
			// check if multipart file is empty or not
			if (!multipartFile.isEmpty()) {
				// check if multipart is valid
				if (userService.isImageValid(multipartFile)) {
					try {
						// save image and get path
						// set path to user object
						String imagePath = productService.uploadToletImage(multipartFile,
								userService.findUserById(id).getPhoneNumber());

						return productService.saveProduct(product);

					} catch (Exception e) {
						System.out.println(e.toString());
					}
				}

			} else {
				System.out.println("File can not be empty!");
			}
			return null;
		} else {
			return null;
		}

	}
	// get all products
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public List<Product> getAllProducts(){
		return productService.getAllProductList();
	}
	
	

}
