package csecarnival.uapadventurers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import csecarnival.uapadventurers.dto.Product;
import csecarnival.uapadventurers.service.SearchService;

@Controller
@RequestMapping(value="/search")
public class SearchController {
	
	@Autowired
	private SearchService searchService;

	// Search Product By Category
	@RequestMapping(value="/{category}",method=RequestMethod.GET)
	@ResponseBody
	public List<Product> searchProductByCategory(@PathVariable("category") String categoryName,@RequestParam("start") Integer start, @RequestParam("size") Integer size){
		return searchService.searchProductByCategory(categoryName,start,size);
	}
	
}
