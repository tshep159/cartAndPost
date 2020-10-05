package org.kamogelofoundations.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kamogelofoundations.dto.Cart;
import org.kamogelofoundations.dto.CartLine;
import org.kamogelofoundations.dto.Category;
import org.kamogelofoundations.dto.Product;
import org.kamogelofoundations.dto.User;
import org.kamogelofoundations.exception.ProductNotFoundException;
import org.kamogelofoundations.repository.CartLineRepository;
import org.kamogelofoundations.repository.CartRepository;
import org.kamogelofoundations.repository.CategoryRepository;
import org.kamogelofoundations.repository.PostRepository;
import org.kamogelofoundations.repository.ProductRepository;
import org.kamogelofoundations.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
public class PageController {

    private final UserService userService;
    
    @Autowired
    ProductRepository productDAO;
    @Autowired
    CategoryRepository categoryDAO;
    
    
    @Autowired
    CartRepository cartDAO;

    @Autowired
    CartLineRepository cartLineDAO;
 
    @Autowired
    PostRepository postDAO;
    
    @Autowired
    public PageController(UserService userService) {
        this.userService = userService;

    }
    
    @RequestMapping(value="user/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, auth);
        
        auth = null;
        
       /* if (auth != null){    
              }*/
        return "redirect:/login?logout";
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView indexn() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "Home");
        modelAndView.setViewName("/login");
        return modelAndView;
    }
        @GetMapping("/home")
    public String home(Principal p, @RequestParam(defaultValue = "0") int page,
            Model model, Cart cart) {
        	
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        	if(!(auth==null)) {
        	 	User user = userService.findUserByEmail(auth.getName());
        	
        		//model.addAttribute("message", "Product has been successfully added inside cart!");
         	    cart.setUser(user);
         	  
        		model.addAttribute("cart", cart);
                
        		List<CartLine> cartlines  = cartLineDAO.findCartLineByCartId(user.getCart().getId());
        		model.addAttribute("count", cartDAO.count());
        		model.addAttribute("cartLine" , cartlines);
        		System.out.println("cartlines....>>>>>>>>>"+ cartlines.size());
          	    model.addAttribute("userName", user.getFirstname() + " " + user.getLastname());
               
               
                model.addAttribute("title", "Welcome " + user.getFirstname());
                model.addAttribute("products", productDAO.findAll());
                model.addAttribute("user", user);
                model.addAttribute("users", user.getFirstname());
                return "/home";
 		
        		
        	}else {
        		return "/";
        	}
           }



    @RequestMapping(value = "/view/user/{id}", method = RequestMethod.GET)
    public ModelAndView veiwUserProfile(@PathVariable long id) {
        ModelAndView model = new ModelAndView();
       
        User user = userService.get(id);
        model.addObject("user", user);
        model.addObject("title", user.getFirstname()+" Service History");
        model.addObject("quiz", postDAO.findAllByAuthorOrderByDateDesc(user));

        model.setViewName("/viewUser");
        return model;
    }


    @RequestMapping(value = "/403")
    public ModelAndView accessDenied() {
        ModelAndView mv = new ModelAndView("/403");
        mv.addObject("errorTitle", "Aha! Caught You.");
        mv.addObject("errorDescription",
                "You are not authorized to view this page!");
        mv.addObject("title", "403 Access Denied");
        return mv;
    }
    
    /*
     * Uploading image
     */
    
	/*
	 * Methods to load all the products and based on category
	 * */
	
	@RequestMapping(value = "/show/all/products")
	public ModelAndView showAllProducts() {		
		ModelAndView mv = new ModelAndView();		
		mv.addObject("title","All Products");
		mv.addObject("products", productDAO.findAll());
		
		//passing the list of categories
		mv.addObject("categories", categoryDAO.findAll());
		
		mv.addObject("userClickAllProducts",true);
		mv.setViewName("/listProducts");
		return mv;				
	}	
	
	@RequestMapping(value = "/show/category/{id}/products")
	public ModelAndView showCategoryProducts(@PathVariable("id") int id) {		
		ModelAndView mv = new ModelAndView();
		
		// categoryDAO to fetch a single category
		Category category = null;
		
		category = categoryDAO.getOne(id);
		
		mv.addObject("title",category.getName());
		
		//passing the list of categories
		//mv.addObject("categories", categoryDAO.findAll());
		
		// passing the single category object
		mv.addObject("category", category);
		
		mv.addObject("userClickCategoryProducts",true);
		return mv;				
	}	
	
	
	/*
	 * Viewing a single product
	 * */
	
	@RequestMapping(value = "/show/{id}/product") 
	public ModelAndView showSingleProduct(@PathVariable int id) throws ProductNotFoundException {
		
		ModelAndView mv = new ModelAndView();
		
		Product product = productDAO.getOne(id);
		
		if(product == null) throw new ProductNotFoundException();
		
		// update the view count
		//product.setViews(product.getViews() + 1);
		productDAO.save(product);
		//---------------------------
		
		mv.addObject("title", product.getName());
		mv.addObject("product", product);
		
		mv.addObject("userClickShowProduct", true);
		
		
		return mv;
		
	}  
	


	 @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	    public StreamingResponseBody getSteamingFile(HttpServletResponse response) throws IOException {
	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=\"Invoice.pdf\"");
	        InputStream inputStream = new FileInputStream(new File("src\\main\\resources\\static\\pdfs\\test2.pdf"));
	        return outputStream -> {
	            int nRead;
	            byte[] data = new byte[1024];
	            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
	                System.out.println("Writing some bytes..");
	                outputStream.write(data, 0, nRead);
	            }
	        };
	    }

    
    

}
