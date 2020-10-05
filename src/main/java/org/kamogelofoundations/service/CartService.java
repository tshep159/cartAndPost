package org.kamogelofoundations.service;

import java.util.List;


import org.kamogelofoundations.dto.Cart;
import org.kamogelofoundations.dto.CartLine;
import org.kamogelofoundations.dto.Product;
import org.kamogelofoundations.dto.User;
import org.kamogelofoundations.repository.CartLineRepository;
import org.kamogelofoundations.repository.CartRepository;
import org.kamogelofoundations.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("cartService")
public class CartService {

	@Autowired
	private CartLineService cartLineDAO;

	@Autowired
	private CartLineRepository cartLineRepo;

	@Autowired
	private ProductRepository productDAO;
	@Autowired
	private UserService us;
	@Autowired
	private CartRepository cartDAO;

	public List<CartLine> getCartLines() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = us.findUserByEmail(auth.getName());
//		Cart cart = cartDAO.findByUserId(user.getId());

		Cart cart = us.findUserByCartid(user.getCart().getId());

		return cartLineDAO.list(cart.getId());

	}

	/* to update the cart count */
	public String manageCartLine(int cartLineId, int count) {

		CartLine cartLine = cartLineDAO.get(cartLineId);

		double oldTotal = cartLine.getTotal();

		Product product = cartLine.getProduct();

		// check if that much quantity is available or not
		if (product.getQuantity() < count) {
			return "result=unavailable";
		}

		// update the cart line
		cartLine.setProductCount(count);
		cartLine.setBuyingPrice(product.getUnitPrice());
		cartLine.setTotal(product.getUnitPrice() * count);
		cartLineDAO.update(cartLine);

		// Get author
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = this.us.findUserByEmail(auth.getName());

		// return cartLineDAO.list(cart.getId());
		// update the cart
//		Cart cart = cartDAO.findByUserId(user.getId());

		Cart cart = us.findUserByCartid(user.getCart().getId());
		cart.setGrandTotal(cart.getGrandTotal() - oldTotal + cartLine.getTotal());
		cartLineDAO.updateCart(cart);

		return "result=updated";
	}

	public String addCartLine(int productId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = this.us.findUserByEmail(auth.getName());
		Cart cart = user.getCart();

		Product product = productDAO.findOne(productId);
		// if(!product == null)

		String response = null;
		CartLine cartLine = cartLineDAO.getByCartAndProduct(cart.getId(), productId);
		if (cartLine == null) {
			// add a new cartLine if a new product is getting added
			cartLine = new CartLine();
			/// Product product = productDAO.findOne(productId);
			// transfer the product details to cartLine
			cartLine.setCart(cart);
			// cartLine.setProduct(product);
			cartLine.setProductCount(1);
			cartLine.setQuantity(+1);
			cartLine.setProduct(product);
			cartLine.setBuyingPrice(product.getUnitPrice());
			cartLine.setTotal(product.getUnitPrice());

			// insert a new cartLine
			cartLineDAO.add(cartLine);

			// update the cart
			cart.setGrandTotal(cart.getGrandTotal() + cartLine.getTotal());
			// cart.setCartLines(cart + 1);
			cartLineDAO.updateCart(cart);

			response = "result=added";
//		} 
//		else {
//			// check if the cartLine has been already reached to maximum count
//			if(cartLine.getProductCount() < 3) {
//				// call the manageCartLine method to increase the count
//				response = this.manageCartLine(cartLine.getId(), cartLine.getProductCount() + 1);				
//			}			
		} else {

			cartLine.setProduct(product);
			cartLine.setProductCount(1);
			cartLine.setQuantity(+1);
			cartLine.setBuyingPrice(product.getUnitPrice());
			cartLine.setTotal(product.getUnitPrice());

			// insert a new cartLine
			// cartLineDAO.add(cartLine);

			// update the cart
			cart.setGrandTotal(cart.getGrandTotal() + cartLine.getTotal());
			// cart.setCartLines(cart + 1);
			cartLineDAO.updateCart(cart);

			response = "result=added";
		}

		return response;
	}
	/*
	 * public Cart getCart() {
	 * 
	 * return ((UserModel)session.getAttribute("userModel")).getCart(); }
	 */

	public Cart get(User c) {

		// Get author
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = us.findUserByEmail(auth.getName());
		// Cart cart = cartDAO.findByUserId(user.getId());

		Cart cart = us.findUserByCartid(user.getCart().getId());
		return cart;
	}

	public String removeCartLine(int cartLineId) {

		CartLine cartLine = cartLineDAO.get(cartLineId);
		/*
		 * // deduct the cart // update the cart // Get author Authentication auth =
		 * SecurityContextHolder.getContext().getAuthentication(); User user =
		 * this.us.findUserByEmail(auth.getName());
		 * 
		 * // Cart cart = cartDAO.findByUserId(user.getId());
		 * 
		 * 
		 * Cart cart = us.findUserByCartid(user.getCart().getId());
		 * cart.setGrandTotal(cart.getGrandTotal() - cartLine.getTotal());
		 * //cart.setCartLines(cart.getCartLines() - 1); cartLineDAO.updateCart(cart);
		 */
		// remove the cartLine
		cartLineRepo.delete(cartLine);

		return "result=deleted";
	}

	public String clearCart(long cartId) {

		Cart cart = cartDAO.getOne(cartId);
		cartDAO.delete(cart);
		return "/home";
	}

	public String validateCartLine() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = this.us.findUserByEmail(auth.getName());

		Cart cart = us.findUserByCartid(user.getCart().getId());

		// cartDAO.findByUserId(user.getId());

		List<CartLine> cartLines = cartLineDAO.list(cart.getId());
		double grandTotal = 0.0;
		int lineCount = 0;
		String response = "result=success";
		boolean changed = false;
		Product product = null;
		for (CartLine cartLine : cartLines) {
			product = cartLine.getProduct();
			changed = false;
			// check if the product is active or not
			// if it is not active make the availability of cartLine as false
			/*
			 * if((!product.&& product.getQuantity() == 0) && cartLine.isAvailable()) {
			 * cartLine.setAvailable(false); changed = true; } // check if the cartLine is
			 * not available // check whether the product is active and has at least one
			 * quantity available if((product.isActive() && product.getQuantity() > 0) &&
			 * !(cartLine.isAvailable())) { cartLine.setAvailable(true); changed = true; }
			 */

			// check if the buying price of product has been changed
			if (cartLine.getBuyingPrice() != product.getUnitPrice()) {
				// set the buying price to the new price
				cartLine.setBuyingPrice(product.getUnitPrice());
				// calculate and set the new total
				cartLine.setTotal(cartLine.getProductCount() * product.getUnitPrice());
				changed = true;
			}

			// check if that much quantity of product is available or not
			if (cartLine.getProductCount() > product.getQuantity()) {
				cartLine.setProductCount(product.getQuantity());
				cartLine.setTotal(cartLine.getProductCount() * product.getUnitPrice());
				changed = true;

			}

			// changes has happened
			if (changed) {
				// update the cartLine
				cartLineDAO.update(cartLine);
				// set the result as modified
				response = "result=modified";
			}

			grandTotal += cartLine.getTotal();
			lineCount++;
		}

		// cart.setCartLines(lineCount++);
		cart.setGrandTotal(grandTotal);
		cartLineDAO.updateCart(cart);

		return response;
	}

	public void save(Cart cart) {
		cartDAO.save(cart);
	}
}
