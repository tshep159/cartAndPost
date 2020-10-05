package org.kamogelofoundations.service;

import org.kamogelofoundations.dto.CartOrder;

public interface CartOrderService {
	
	
	CartOrder addCartOrder(CartOrder order);
	
	CartOrder getCartOrder(long orderID);
	
	
	

}
