package org.kamogelofoundations.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.validation.Valid;

import org.kamogelofoundations.dto.Product;
import org.kamogelofoundations.dto.User;
import org.kamogelofoundations.exception.ProductNotFoundException;
import org.kamogelofoundations.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController1 {

	@Autowired
	ProductRepository productDAO;
	
	
	///@RequestMapping(value = { "/show/all/products" })
	public ModelAndView showAllProducts() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "All Products");
		// passing the list of categories
	///	mv.addObject("categories", categoryDAO.list());
		mv.addObject("product", productDAO.findAll());
		mv.setViewName("/products");
		mv.addObject("userClickAllProducts", true);
		return mv;
	}
	
	@RequestMapping("/viewProduct/{id}")
	public String viewProduct(@PathVariable(value="id") int id, Model model){
		Product product = productDAO.getOne(id);
		model.addAttribute("product", product);
		
		return "/viewProductDetail";
	}

	
		
		/*
	@RequestMapping(value = { "/show/category/{id}/products" })
	public ModelAndView showCategoryProducts(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("page");
		// categoryDAO to fetch a single category
		Category category = null;

		category = categoryDAO.get(id);

		mv.addObject("title", category.getName());
		
		// passing the list of categories
		mv.addObject("categories", categoryDAO.list());
		
		// passing the single category object
		mv.addObject("category", category);
		
		mv.addObject("userClickCategoryProducts", true);
		return mv;

	@RequestMapping(value = {"/show/{id}/product"})
	public ModelAndView showSingleProduct(@PathVariable("id") int id) throws ProductNotFoundException{
		ModelAndView mv = new ModelAndView();
		
		Product product = productDAO.getOne((long) id);
		
		if(product == null)
			throw new ProductNotFoundException();
		
		//update the view count
		product.setViews(product.getViews() + 1);
		productDAO.save(product);
		//-----------
		
		mv.addObject("title", product.getName());
		mv.addObject("product",product);
		mv.addObject("userClickShowProduct", true);
			
		return mv;
	}
	
	 @RequestMapping(value = "/add/product", method = RequestMethod.GET)
	    public String registration(Model model) {

	        model.addAttribute("user", new Product());
	        model.addAttribute("title", "Add Product");
	        return "/addProduct";
	    }

	  
	 
	 @RequestMapping(value = "/add/product", method = RequestMethod.POST)
	    public String createNewUser(@Valid @ModelAttribute Product p,
	            BindingResult bindingResult, Model model) {


	        model.addAttribute("title", "Problem Adding product");
	        if (!bindingResult.hasErrors()) {
	        	//add product

	                    productDAO.save(p);
	                    model.addAttribute("successMessage",
	                            "Product added successfully");
	                    model.addAttribute("user", new User());
	                    model.addAttribute("title", "Product add success");
	                }
	        
	        return "/addProduct";
	    }
	
	  public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";
	  @RequestMapping("/uploads")
	  public String UploadPage(Model model) {
		  return "/uploadview";
	  }
	  @RequestMapping("/upload")
	  public String upload(Model model,@RequestParam("files") MultipartFile[] files) {
		  StringBuilder fileNames = new StringBuilder();
		  for (MultipartFile file : files) {
			  Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
			  fileNames.append(file.getOriginalFilename()+" ");
			  try {
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		  }
		  model.addAttribute("msg", "Successfully uploaded files "+fileNames.toString());
		  return "/uploadstatusview";
	  }
	  
	
	*/
	
}
