package org.kamogelofoundations.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.kamogelofoundations.repository.OrderRepo;
import org.kamogelofoundations.service.CartOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

	
	
		@Autowired
	CartOrderService service;

	@Autowired
	OrderRepo or;
	
@RequestMapping(value="/download", method=RequestMethod.GET)
public ResponseEntity<Object> download()throws IOException{
	
	
	String fileName= "/src/main/resources/static/pdfs/test2.pdf";
	File file = new File(fileName);
	InputStreamResource resource = new InputStreamResource(new FileInputStream (file));
	
	HttpHeaders headers = new HttpHeaders();
	headers.add("Content-Disposition",

			String.format("attachment; fileName =\"%s\"", file.getName()));
	headers.add("Cache-Ccontrol","no-cache, no-store,must-revalidate");
	headers.add("Pragma", "no-cache");
	headers.add("Expires", "0");
	
	return ResponseEntity.ok()
		.contentType(MediaType.parseMediaType(fileName))
		.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
		.body(resource.getFile());
}	
	



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/list/orders")
	public String listOrders(Model model) {
		
		model.addAttribute("orders", or.findAll());
		
		return null;
		
		
		
	}
	
}

