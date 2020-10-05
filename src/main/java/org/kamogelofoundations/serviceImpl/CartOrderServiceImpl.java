package org.kamogelofoundations.serviceImpl;

import org.kamogelofoundations.dto.CartOrder;
import org.kamogelofoundations.repository.CartOrderRepository;
import org.kamogelofoundations.service.CartOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartOrderServiceImpl  implements CartOrderService{

	@Autowired
	private CartOrderRepository orderDAO;
	
	
	
	@Override
	public CartOrder addCartOrder(CartOrder order) {

		return orderDAO.save(order);
	}

	@Override
	public CartOrder getCartOrder(long orderId) {
		// TODO Auto-generated method stub
		return orderDAO.findOne(orderId);
	}

}
