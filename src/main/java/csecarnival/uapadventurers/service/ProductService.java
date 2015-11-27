package csecarnival.uapadventurers.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import csecarnival.uapadventurers.dto.Product;
import csecarnival.uapadventurers.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	public Product saveProduct(Product product) {
		return productRepository.saveAndFlush(product);
	}

	public Date convertStringToDate(String dateString) {
		Date date = null;
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			date = df.parse(dateString);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return date;
	}

	// upload image to server.
	public String uploadToletImage(MultipartFile multipartFile, String phoneNumber) throws Exception {
		byte[] bytes = multipartFile.getBytes();

		String rootPath = System.getProperty("catalina.home");
		File directory = new File(rootPath + File.separator + "AppData" + File.separator + "farmerbazzar"
				+ File.separator + phoneNumber + File.separator + "data");
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

	// get All products
	public List<Product> getAllProductList() {
		return productRepository.findAll();
	}

	// get specific product by id
	public Product getProductById(Long id) {
		return productRepository.findById(id);
	}


	// Pagination
	public List<Product> getProductByRange(Integer start, Integer size) {
		Page<Product> page= productRepository.findAll(new PageRequest(start, size));
		return page.getContent();
	}

	// get product list by subcategory
	public List<Product> getProductsBySubCategory(String subCategoryName) {
		return productRepository.findBySubCategoryName(subCategoryName);
	}
	
	

}
