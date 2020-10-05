package org.kamogelofoundations.repository;

import java.util.List;

import org.kamogelofoundations.dto.Cart;
import org.kamogelofoundations.dto.CartLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartLineRepository extends JpaRepository<CartLine, Integer> {

	// @Query("update User u set u.lastLogin = CURRENT_TIMESTAMP where u.username =
	// ?1")
//    int updateLastLogin(String username);

//
	@Query("FROM CartLine WHERE  cart.id = :cartId")
	public List<CartLine> findCartLineByCartId(@Param("cartId") long cartId);

	@Query("FROM CartLine WHERE  product.id = :productId")
	public int getProductFromCartLine(@Param("productId") int productId);

	//
//	@Query("FROM CartLine WHERE cartId = :cartId")
//	public List<CartLine> listAllCartLines(@Param("cartId")int cartId);
//	public CartLine get(int id);	

	// public boolean add(CartLine cartLine);
	/// public boolean update(CartLine cartLine);
	// public boolean remove(CartLine cartLine);

	// fetch the CartLine based on cartId and productId
//
	@Query("FROM CartLine WHERE cartId = :cartId AND product.id = :productId")
	public CartLine getByCartAndProduct(@Param("cartId") int cartId, @Param("productId") int productId);

//	
//		
	// updating the cart
	// boolean updateCart(Cart cart);
	@Query("FROM CartLine WHERE cart_id = :cartId")
	public List<CartLine> findAllByCart(@Param("cartId") Cart cart);

	// public List<CartLine> findAllByCart(Cart cart);

	// list of available cartLine
//	@Query("FROM CartLine WHERE cartId = :cartId AND available = :available")
//	public List<CartLine> listAvailable(@Param ("cartId")int cartId);

	// adding order details
//	boolean addOrderDetail(OrderDetail orderDetail);

}
