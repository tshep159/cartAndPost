package org.kamogelofoundations.serviceImpl;

import org.kamogelofoundations.dto.CartOrder;
import org.kamogelofoundations.repository.OrderRepo;
import org.kamogelofoundations.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceimpl implements OrderService{

	@Autowired
	OrderRepo or;
	
	@Override
	public void saveOrder(CartOrder order) {
		or.save(order);
	}

	@Override
	public void displayOrders() {

		
		or.findAll();
		
	}

	
	
}
