package org.kamogelofoundations;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
@EnableAutoConfiguration
@SpringBootApplication
@CrossOrigin(origins="http://localhost:8081")
public class App{

    public static Logger log = Logger.getLogger(App.class.getName());
    
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		
		
	}
}
 