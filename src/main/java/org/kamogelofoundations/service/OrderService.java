package org.kamogelofoundations.service;

import org.kamogelofoundations.dto.CartOrder;

public interface OrderService {
	
	
	void saveOrder(CartOrder order);
	void displayOrders();
	

}
