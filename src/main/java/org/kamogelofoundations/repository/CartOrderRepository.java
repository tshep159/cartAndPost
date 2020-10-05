package org.kamogelofoundations.repository;

import java.util.List;

import org.kamogelofoundations.dto.CartOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartOrderRepository extends JpaRepository<CartOrder, Long> {

	List<CartOrder> findOrdersByCustomer(long id);

	List<CartOrder> findCartOrderByCart(long id);

	CartOrder getByCustomer(long id);

}
