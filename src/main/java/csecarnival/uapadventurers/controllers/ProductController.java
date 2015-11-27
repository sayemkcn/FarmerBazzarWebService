package csecarnival.uapadventurers.controllers;

import java.io.EOFException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
						product.setProductImagePath(imagePath);
						System.out.println("IMAGEPATH"+product.getProductImagePath());
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
	
	// find product by specific id
	@RequestMapping(value="",method=RequestMethod.GET)
	@ResponseBody
	public Product getProductById(@RequestParam("id") Long id){
		return productService.getProductById(id);
	}
	
	// find product paginated (by range)
	@RequestMapping(value="/range",method=RequestMethod.GET)
	@ResponseBody
	public List<Product> getProductByRange(@RequestParam Map<String, String> map){
		int start = Integer.parseInt(map.get("start"));
		int size = Integer.parseInt(map.get("size"));
		return productService.getProductByRange(start, size);
	}
	
	// Find all categories
	public List<String> getAllCategories(){
		return productService.getAllCategories();
	}
}
