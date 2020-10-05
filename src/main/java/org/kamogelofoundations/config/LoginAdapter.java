package org.kamogelofoundations.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;

@Configuration
public class LoginAdapter implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

	@Override
	public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
		// TODO Auto-generated method stub
		
	}

   /* private final UserService userService;

    @Autowired
    public LoginAdapter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
      userService.updateLastLogin(event.getAuthentication().getName());
    }*/

}
