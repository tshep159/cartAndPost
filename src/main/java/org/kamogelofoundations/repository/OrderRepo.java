package org.kamogelofoundations.repository;

import java.util.List;

import org.kamogelofoundations.dto.CartOrder;
import org.kamogelofoundations.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<CartOrder, Long> {

	List<CartOrder> findAllByCustomerOrderByDateDesc(User customer);

}
