package org.kamogelofoundations.serviceImpl;

import java.util.List;

import org.kamogelofoundations.dto.Product;
import org.kamogelofoundations.repository.ProductRepository;
import org.kamogelofoundations.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository pr;

	@Override
	public Product get(int productId) {
		// TODO Auto-generated method stub
		return pr.getOne((int) productId);
	}

	@Override
	public List<Product> list() {
		// TODO Auto-generated method stub
		return pr.findAll();
	}

	@Override
	public Product add(Product product) {
		// TODO Auto-generated method stub
		return pr.save(product);
	}

	@Override
	public Product update(Product product) {
		// TODO Auto-generated method stub
		return pr.save(product);
	}

	@Override
	public Product delete(Product product) {
		// TODO Auto-generated method stub
		return null;// pr.delete(product);
	}

	@Override
	public List<Product> listActiveProducts() {
		// TODO Auto-generated method stub
		return null;//pr.findByActiveTrue();
	}

	@Override
	public List<Product> listActiveProductsByCategory(int categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getLatestActiveProducts(int count) {
		// TODO Auto-generated method stub
		return null;
	}
}
