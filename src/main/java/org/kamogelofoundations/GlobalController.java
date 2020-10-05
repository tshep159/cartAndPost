package org.kamogelofoundations;

import javax.servlet.http.HttpSession;

import org.kamogelofoundations.dto.User;
import org.kamogelofoundations.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {
	
	
	@Autowired
	private UserService userDAO;
	
	@Autowired
	private HttpSession session;
	
	private UserModel userModel = null;
	private User user = null;	
	
	
	@ModelAttribute("userModel")
	public UserModel getUserModel() {		
		if(session.getAttribute("userModel")==null) {
			// get the authentication object
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			
//		if(!authentication.getPrincipal().equals("anonymousUser")){
				// get the user from the database
//				user = userDAO.findUserByEmail(authentication.getName());
				
				if(user!=null) {
					// create a new model
					userModel = new UserModel();
					// set the name and the id
					userModel.setId(user.getId());
					userModel.setFullName(user.getFirstname() + " " + user.getLastname());
//					userModel.setRoles(user.getRoles());
						userModel.setCart( user.getCart());					
	
					session.setAttribute("userModel", userModel);
					return userModel;
				}			
			}
	//	}
		
		return (UserModel) session.getAttribute("userModel");		
	}
		
	
	
	
}