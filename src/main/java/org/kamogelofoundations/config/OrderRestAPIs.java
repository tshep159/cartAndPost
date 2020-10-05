package org.kamogelofoundations.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.kamogelofoundations.dto.Cart;
import org.kamogelofoundations.dto.CartLine;
import org.kamogelofoundations.dto.CartOrder;
import org.kamogelofoundations.dto.User;
import org.kamogelofoundations.service.CartLineService;
import org.kamogelofoundations.service.CartService;
import org.kamogelofoundations.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderRestAPIs {


	 @Autowired
	  CartLineService customerRepository;
	 @Autowired
	 UserService us;
	 
	 @Autowired
	 CartService cs;
	    @GetMapping(value = "",
	            produces = MediaType.APPLICATION_PDF_VALUE)
	    public ResponseEntity<InputStreamResource> customersReport() throws IOException {
	    	
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = this.us.findUserByEmail(auth.getName());
			Cart cart = cs.get(user);
		
	    	
	        List<CartLine> customers = (List<CartLine>) customerRepository.list(cart.getId());
	 
	        ByteArrayInputStream bis = PDFGenerator.customerPDFReport(customers);
	 
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "inline; filename=customers.pdf");
	 
	        return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(bis));
	    }


}
