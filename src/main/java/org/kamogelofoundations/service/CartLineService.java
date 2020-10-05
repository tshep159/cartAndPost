package org.kamogelofoundations.service;

import java.util.List;

import org.kamogelofoundations.dto.Cart;
import org.kamogelofoundations.dto.CartLine;
import org.springframework.data.repository.query.Param;

public interface CartLineService {
	
	
	
	public List<CartLine> list(long cartId);
	public CartLine get(int id);	
	public CartLine add(CartLine cartLine);
	public CartLine update(CartLine cartLine);
	public CartLine remove(CartLine cartLine);
	// fetch the CartLine based on cartId and productId
	public CartLine getByCartAndProduct(long cartId, int productId);		
	
	public List<CartLine> getCartLinesByUserAndCart(int cartId, long userId);
		
	// updating the cart
	Cart updateCart(Cart cart);
	
	// list of available cartLine
	public List<CartLine> listAvailable(int cartId);
	
	public List<CartLine> findCartLineByCartId(int cartId);
	

	public void getProductFromCartLine(@Param("productId") int productId);
	
	
	void removeCartLine(int cartLineId);

	// adding order details
//	boolean addOrderDetail(OrderDetail orderDetail);

}
