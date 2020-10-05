
package org.kamogelofoundations.serviceImpl;

import java.util.List;

import org.kamogelofoundations.dto.Cart;
import org.kamogelofoundations.dto.CartLine;
import org.kamogelofoundations.dto.User;
import org.kamogelofoundations.repository.CartLineRepository;
import org.kamogelofoundations.repository.CartRepository;
import org.kamogelofoundations.service.CartLineService;
import org.kamogelofoundations.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartLineServiceImpl implements CartLineService {

	@Autowired
	CartLineRepository cr;
	@Autowired
	CartRepository cartDao;
	@Autowired
	UserService us;

	@Override
	public List<CartLine> list(long cartId) {
		// TODO Auto-generated method stub
		return cr.findCartLineByCartId(cartId);
	}

	@Override
	public CartLine get(int id) {
		// TODO Auto-generated method stub
		return cr.getOne(id);
	}

	@Override
	public CartLine add(CartLine cartLine) {
		// TODO Auto-generated method stub
		return cr.save(cartLine);
	}

	@Override
	public CartLine update(CartLine cartLine) {
		// TODO Auto-generated method stub
		return cr.save(cartLine);
	}

	@Override
	public void removeCartLine(int cartLineId) {
		CartLine c = cr.getOne(cartLineId);
		// Get author
		System.out.println("attaching user to cart");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = this.us.findUserByEmail(auth.getName());
		Cart cart = us.findUserByCartid(user.getCart().getId());

		System.out.println("getting cart by user cart");
		// deduct the cart
		cart.setGrandTotal(cart.getGrandTotal() - c.getTotal());

		System.out.println("getting cart total" + cart.getGrandTotal());

		// update the cart
		cartDao.save(cart);
		System.out.println("Saved cart ");
		cr.delete(c);
		System.out.println("deleted cartLine");
	}

	@Override
	public CartLine getByCartAndProduct(long cartId, int productId) {
		// TODO Auto-generated method stub
		return null;// cr.getByCartAndProduct(cartId, productId);
	}

	@Override
	public Cart updateCart(Cart cart) {
		// TODO Auto-generated method stub
		return cartDao.save(cart);
	}

	@Override
	public List<CartLine> listAvailable(int cartId) {
		// TODO Auto-generated method stub
		return null; // cr.listAvailable(cartId);
	}

	@Override
	public List<CartLine> getCartLinesByUserAndCart(int cartId, long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CartLine> findCartLineByCartId(int cartId) {
		// TODO Auto-generated method stub
		return cr.findCartLineByCartId(cartId);

	}

	@Override
	public void getProductFromCartLine(int productId) {

		cr.getProductFromCartLine(productId);
	}

	@Override
	public CartLine remove(CartLine cartLine) {
		// TODO Auto-generated method stub
		return null;
	}

}
