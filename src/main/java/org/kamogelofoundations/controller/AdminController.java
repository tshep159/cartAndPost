package org.kamogelofoundations.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.kamogelofoundations.dto.Cart;
import org.kamogelofoundations.dto.CartLine;
import org.kamogelofoundations.dto.CartOrder;
import org.kamogelofoundations.dto.Category;
import org.kamogelofoundations.dto.Post;
import org.kamogelofoundations.dto.Product;
import org.kamogelofoundations.dto.User;
import org.kamogelofoundations.repository.CartLineRepository;
import org.kamogelofoundations.repository.CartRepository;
import org.kamogelofoundations.repository.CategoryRepository;
import org.kamogelofoundations.repository.OrderRepo;
import org.kamogelofoundations.repository.PostRepository;
import org.kamogelofoundations.repository.ProductRepository;
import org.kamogelofoundations.service.AdminService;
import org.kamogelofoundations.service.PostService;
import org.kamogelofoundations.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "admin")
public class AdminController {

	private final AdminService userService;
	private final UserService us;

	@Autowired
	private OrderRepo or;
	@Autowired
	CartLineRepository crepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private CartRepository cartDAO;

	@Autowired
	PostService postDAO;

	@Autowired
	private PostRepository postRepo;

	@GetMapping
	public String index(Model model) {

		List<Product> products = productRepo.findAll();
		List<Category> categories = cr.findAll();

		HashMap<Integer, String> categoriesMap = new HashMap<>();
		categories.forEach(c -> categoriesMap.put(c.getId(), c.getName()));

		model.addAttribute("products", products);
		model.addAttribute("categoriesMap", categoriesMap);

		// long count = productRepo.count();
		// double pageCount = Math.ceil((double)count / (double)perPage);

		return "/admin/products/index";
	}

	@GetMapping("/add")
	public String addP(Product product, Model model) {

		List<Category> categories = cr.findAll();
		model.addAttribute("categories", categories);

		return "/admin/products/add";
	}

	@PostMapping("/add")
	public String addFF(@Valid Product product, BindingResult bindingResult, MultipartFile file,
			RedirectAttributes redirectAttributes, Model model) throws IOException {

		List<Category> categories = cr.findAll();

		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categories);
			return "/admin/products/add";
		}

		boolean fileOK = false;
		byte[] bytes = file.getBytes();
		String filename = file.getOriginalFilename();
		Path path = Paths.get("src/main/resources/static/media/" + filename);

		if (filename.endsWith("jpg") || filename.endsWith("png"))
			fileOK = true;

		redirectAttributes.addFlashAttribute("message", "Product added");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		String slug = product.getName().toLowerCase().replace(" ", "-");

		Product productExists = productRepo.findBySlug(slug);

		if (!fileOK) {
			redirectAttributes.addFlashAttribute("message", "Image must be a jpg or a png");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("product", product);
		} else if (productExists != null) {
			redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("product", product);
		} else {
			product.setSlug(slug);
			product.setImage(filename);
			productRepo.save(product);

			Files.write(path, bytes);
		}

		return "redirect:/admin/products/add";
	}

	@Autowired
	CategoryRepository cr;

	@Autowired
	public AdminController(AdminService userService, UserService us) {
		this.userService = userService;
		this.us = us;
	}

	/*
	 * Fetch and display all users from the database
	 */
	@RequestMapping(value = "/all/users", method = RequestMethod.GET)
	public ModelAndView allUsers() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("users", userService.listUsers());// users object
		modelAndView.addObject("category", new Category());

		List<CartOrder> list = or.findAll();

		modelAndView.addObject("orders", list);

		// list categories
		List<Category> categories = cr.findAll();
		modelAndView.addObject("categories", categories);

		// list posts

		List<Post> posts = postRepo.findAll();
		modelAndView.addObject("posts", posts);

		modelAndView.addObject("total", userService.TotalUsers());// users count object

		modelAndView.addObject("title", "All users");
		modelAndView.setViewName("/admin/users");
		return modelAndView;
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ModelAndView saveUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		model.addObject("user");
		if (bindingResult.hasErrors()) {
			System.out.print(bindingResult.getAllErrors());
			model.setViewName("/admin/userform");
		} else {
			userService.updateUser(user);
			model.addObject("users", userService.listUsers());
			model.setViewName("/admin/users");
		}
		return model;
	}

	/**
	 * Activate and Deactivate User
	 *
	 * @param id
	 * @param page
	 * @return
	 */
	/*
	 * Block and unblock the user from the system
	 * 
	 */
	@RequestMapping(value = "/activate/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView check(@PathVariable Long id, @RequestParam(defaultValue = "0") int page) {

		ModelAndView mv = new ModelAndView();

		// list categories
		List<Category> categories = cr.findAll();
		mv.addObject("categories", categories);

		// list posts

		List<Post> posts = postRepo.findAll();
		mv.addObject("posts", posts);

		User webUser = userService.get(id);
		boolean isActive = webUser.getActive();
		webUser.setActive(!isActive);
		userService.updateUser(webUser);
		mv.addObject("title", "User Activation & Deactivation");
		mv.addObject("users", userService.listUsers());
		mv.addObject("total", userService.TotalUsers());

		mv.addObject("adminMessage", (isActive) ? webUser.getFirstname() + " Has Been Dectivated Successfully!"
				: webUser.getFirstname() + " Has Been Activated Successfully");
		mv.setViewName("/admin/users");
		return mv;
	}

	/*
	 * view user quiz history
	 */
	@RequestMapping(value = "/view/user/{id}", method = RequestMethod.GET)
	public ModelAndView veiwUserProfile(@PathVariable long id) {
		ModelAndView model = new ModelAndView();

		User user = us.get(id);
		List<Post> posts = postRepo.findAllByAuthorOrderByDateDesc(user);

		List<Cart> userCarts = cartDAO.findAllByUserOrderByIdDesc(user); // GETTING CART BY USER
		model.addObject("carts", userCarts);
		List<CartOrder> orders = or.findAllByCustomerOrderByDateDesc(user);
		model.addObject("orders", orders);
		// Define variables to be passed to view
		model.addObject("posts", posts);

		model.addObject("user", user);

		model.setViewName("/viewUser");
		return model;
	    
	
	}

	
	@RequestMapping(value = "/view/cart/{id}", method = RequestMethod.GET)
	public ModelAndView veiwUserProfileCartLines(@PathVariable long id) {

		
		ModelAndView model = new ModelAndView();

		Cart cart = cartDAO.getOne(id);//getting cart
		
		
		List<CartLine> userCartLines = crepo.findCartLineByCartId(cart.getId());
		
		InvoiceHelper ih = new InvoiceHelper();
		ih.generatePDF(userCartLines);
		model.addObject("cartLine", userCartLines);
		model.addObject("cart", cart);
		
		model.setViewName("/details");
		return model;
	}

	

	@RequestMapping(value = "/view/user", method = RequestMethod.POST)
	public String createNewUser(Model model, @PathVariable long id) {
		List<Post> posts = postDAO.findAll();

		// Define variables to be passed to view
		model.addAttribute("posts", posts);
		return "/viewUser";
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ModelAndView allcat() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("categories", cr.findAll());// users object

		// list posts

		List<Post> posts = postRepo.findAll();
		modelAndView.addObject("posts", posts);

		modelAndView.addObject("order", or.findAll());

		modelAndView.addObject("total", userService.TotalUsers());// users count object

		modelAndView.addObject("title", "All users");
		modelAndView.setViewName("/admin/users");
		return modelAndView;
	}

	@GetMapping("/categories/add")
	public String add(Category category, Model model) {

		List<Category> categories = cr.findAll();
		model.addAttribute("categories", categories);

		return "/admin/categories/add";
	}

	@PostMapping("/categories/add")
	public String add(@Valid Category category, BindingResult bindingResult, MultipartFile file,
			RedirectAttributes redirectAttributes, Model model) throws IOException {

		List<Category> categories = cr.findAll();
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categories);
			return "/admin/categories/add";

		}
		cr.save(category);

		return "redirect:/admin/all/users";
	}

}
