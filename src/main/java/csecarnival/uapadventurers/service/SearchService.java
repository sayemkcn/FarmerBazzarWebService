package csecarnival.uapadventurers.service;

import java.util.List;

import javax.print.attribute.standard.PageRanges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import csecarnival.uapadventurers.dto.Product;
import csecarnival.uapadventurers.repository.ProductRepository;

@Service
public class SearchService {
	@Autowired
	private ProductRepository productRepository;

	public List<Product> searchProductByCategory(String categoryName, Integer start, Integer size) {
		List<Product> list = productRepository.findByCategoryName(categoryName);
		return list.subList(start, start+size);
	}

}
