package org.kamogelofoundations.controller;

import java.util.List;
import java.util.function.Consumer;

import org.kamogelofoundations.dto.Cart;
import org.kamogelofoundations.dto.CartLine;
import org.kamogelofoundations.dto.CartOrder;
import org.kamogelofoundations.dto.Product;
import org.kamogelofoundations.dto.User;
import org.kamogelofoundations.repository.CartLineRepository;
import org.kamogelofoundations.repository.CartRepository;
import org.kamogelofoundations.repository.UserRepository;
import org.kamogelofoundations.service.CartLineService;
import org.kamogelofoundations.service.CartOrderService;
import org.kamogelofoundations.service.CartService;
import org.kamogelofoundations.service.MailService;
import org.kamogelofoundations.service.ProductService;
import org.kamogelofoundations.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cart")
public class CartController {

//	private final static Logger logger = LoggerFactory.getLogger(CartController.class);

	private CartOrderService orderDAO;
	private CartService cartService;
	private ProductService productDAO;
	private CartLineRepository crepo;
	private CartLineService cartlineDAO;
	private UserService us;
	private CartRepository cartDAO;
	@Autowired
	public CartController(CartOrderService orderDAO, CartRepository cartOrderRepo, CartService cartService,
			ProductService productDAO, CartLineRepository crepo, CartLineService cartlineDAO, UserService us,
			CartRepository cartDAO, MailService mailService) {
		super();
		this.orderDAO = orderDAO;
		this.cartService = cartService;
		this.productDAO = productDAO;
		this.crepo = crepo;
		this.cartlineDAO = cartlineDAO;
		this.us = us;
		this.cartDAO = cartDAO;
	}

	@RequestMapping("/show")
	public ModelAndView showCart(@RequestParam(name = "result", required = false) String result) {
		System.out.println("====================================================================enter /show");

		ModelAndView mv = new ModelAndView("/cart/cart");
		mv.addObject("title", "Shopping Cart");
		if (result != null) {
			switch (result) {
			case "added":
				mv.addObject("message", "Product has been successfully added inside cart!");
				cartService.validateCartLine();
				break;
			case "unavailable":
				mv.addObject("message", "Product quantity is not available!");
				break;
			case "updated":
				mv.addObject("message", "Cart has been updated successfully!");
				cartService.validateCartLine();
				break;
			case "modified":
				mv.addObject("message", "One or more items inside cart has been modified!");
				break;
			case "maximum":
				mv.addObject("message", "Maximum limit for the item has been reached!");
				break;
			case "deleted":
				mv.addObject("message", "CartLine has been successfully removed!");
				break;

			}
		} else {
			String response = cartService.validateCartLine();
			if (response.equals("result=modified")) {
				mv.addObject("message", "One or more items inside cart has been modified!");
			}
		}
		return mv;

	}

	@RequestMapping("/viewCart/{id}")
	public String viewCart(@PathVariable(value = "id") long id, Model model) {
		Cart cart = cartDAO.getOne(id);
		model.addAttribute("product", cart);
		List<CartLine> lines = crepo.findCartLineByCartId(id);
		model.addAttribute("list", lines);
		return "/viewCartDetail";
	}

	@RequestMapping("/{cartLineId}/update")
	public String udpateCartLine(@PathVariable int cartLineId, @RequestParam int count) {
		String response = cartService.manageCartLine(cartLineId, count);
		return "redirect:/cart/show?" + response;
	}

	@RequestMapping("/create")
	public String createCart() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = this.us.findUserByEmail(auth.getName());
		
		if(user.getCart()==null  ) {
			Cart cart = new Cart();	
			cart.setUser(user);
			cartService.save(cart);

			user.setCart(cart);
			us.updateUser(user);
		
			return "redirect:/home";
		}else if(user.getCart().isSaved()==true) {
			
			Cart cart = new Cart();	
			cart.setUser(user);
			cartService.save(cart);

			user.setCart(cart);
			us.updateUser(user);
		

			return "redirect:/home";
			
			
		}
		
		
		
	return "redirect:/home";
	}

	@RequestMapping("/add/{productId}/product")
	public String addCartLine(@PathVariable int productId) {
		ModelAndView mv = new ModelAndView();
		Product p = productDAO.get(productId);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = this.us.findUserByEmail(auth.getName());
		String response = cartService.addCartLine(p.getId());
		mv.addObject("message", "Product has been successfully added inside cart!");

		List<CartLine> cartLines = crepo.findCartLineByCartId(user.getCart().getId());

		mv.addObject("cartLine", cartLines);

		// return "redirect:/home?"+response;
		// validateCart();
		return "redirect:/home";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/checkout")
	public String createOrder() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = this.us.findUserByEmail(auth.getName());

		Cart cart = cartDAO.findOne(user.getCart().getId());
		cart.setSaved(true);
		cartDAO.save(cart);
		System.out.println("m=====================================cart id  " + cart.getId());
		List<CartLine> namesList = crepo.findCartLineByCartId(user.getCart().getId());
		
		//System.out.println("sending email " + order.getId());
//		InvoiceHelper e = new InvoiceHelper();
//		e.generatePDF(namesList);

		for (CartLine namesList1 : namesList) {

			CartOrder order = new CartOrder();
			order.setCart(cart);

			order.setCartGrandTotal(cart.getGrandTotal());
			order.setCustomer(user);
			order.getCartlines();

			System.out.println("Done generating pdf");
			orderDAO.addCartOrder(order);
		
		}
		
		return "redirect:/cart/create";
	}

	@RequestMapping("/remove/{cartLineId}")
	public String removeCartLine(@PathVariable int cartLineId) {
		// String response = cartService.removeCartLine(cartLineId);

		CartLine c = cartlineDAO.get(cartLineId);
		Cart cart = c.getCart();
		// deduct the cart
		cart.setGrandTotal(cart.getGrandTotal() - c.getTotal());
		// update the cart
		cartDAO.save(cart);
		crepo.delete(cartLineId);
		return "redirect:/home";// +response;
	}

	@RequestMapping("delete/cart/{cardId}")
	public String clearCart(@PathVariable int cartId) {

		cartService.clearCart(cartId);

		return "/home";
	}

	/*
	 * after validating it redirect to checkout if result received is success
	 * proceed to checkout else display the message to the user about the changes in
	 * cart page
	 */
	@RequestMapping("/validate")
	public String validateCart() {
		String response = cartService.validateCartLine();
		if (!response.equals("result=success")) {
			return "redirect:/cart/show?" + response;
		} else {
			return "redirect:/cart/checkout";
		}
	}
}