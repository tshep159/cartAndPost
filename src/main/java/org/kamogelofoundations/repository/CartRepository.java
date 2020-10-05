package org.kamogelofoundations.repository;

import java.util.List;

import org.kamogelofoundations.dto.Cart;
import org.kamogelofoundations.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	
  @Query("FROM CartLine WHERE userId = :userId")
	public Cart getUserbyCartId(@Param("userId") long userId);	
 
   
   @Query("FROM User WHERE cart_id = :cartId")
   public Cart findCartByUserId(@Param ("cartId") long id);
   
   
   List<Cart>findAllByUserOrderByIdDesc(User user);
//	
//   @Query("FROM User WHERE cart_id = :cartId")
//   public long getCartIdFromUserTable(@Param ("cartId") long id);
    
  
//   @Query("FROM User WHERE cart_id = :cartId")
//  public Cart findCartByUser(@Param ("cartId")long id);

}
