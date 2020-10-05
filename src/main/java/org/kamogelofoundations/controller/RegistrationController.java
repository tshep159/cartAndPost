package org.kamogelofoundations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;
import org.kamogelofoundations.dto.User;
import org.kamogelofoundations.service.UserService;
import org.springframework.web.bind.annotation.ModelAttribute;


/*
public class RegistrationController {

// private final UserService userService;
/*
    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
  }*/
/*
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {

        model.addAttribute("user", new User());
        model.addAttribute("title", "Sign up");
        return "/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createNewUser(@Valid @ModelAttribute User user,
            BindingResult bindingResult, Model model) {
        String[] word = {"fuck", "pussy", "marete", "kuku", "dick"};//array variable for rejecting unwanted username
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }

        //filtering username 
        for (String w : word) {
            if (user.getUsername().contains(w)) {

                bindingResult.rejectValue("username", "error.user",
                        "username cant be set to " + user.getUsername());
            }
        }

        model.addAttribute("title", "Sign up errors");
        if (!bindingResult.hasErrors()) {
            // Registration successful, save user
            // Set user role to USER and set it as active

            //capitalizing the first letter of the user's firstname
            String str = user.getFirst_name();
            //capitalizing the first letter of the user's lastname
            String str2 = user.getLastName();
            StringBuilder result = new StringBuilder(str.length());

            StringBuilder builder = new StringBuilder(str2.length());
            String wordstr2[] = str2.split("\\ ");
            String words[] = str.split("\\ ");
            for (int i = 0; i < words.length; i++) {
                result.append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1)).append(" ");

                for (int x = 0; x < wordstr2.length; x++) {
                    builder.append(Character.toUpperCase(wordstr2[x].charAt(0))).append(wordstr2[x].substring(1)).append(" ");

                    //printing the output
                    System.out.println(result);
                    System.out.println(builder);

                    User p = new User();
                    p.setFirst_name(result.toString());
                    p.setLastName(builder.toString());
                    p.setEmail(user.getEmail().trim().toLowerCase());
                    p.setUsername(user.getUsername().trim().toLowerCase());
                    p.setCity(user.getCity().trim().toLowerCase());
                    p.setAddressLineOne(user.getAddressLineOne().trim().toLowerCase());
                    p.setAddressLineTwo(user.getAddressLineTwo().trim().toLowerCase());
                    p.setContactNumber(user.getContactNumber());
                    p.setPassword(user.getPassword());
                    p.setPostalCode(user.getPostalCode());
                    p.setIdNumber(user.getIdNumber());
                

                    userService.save(p);
                    model.addAttribute("successMessage",
                            "User has been registered successfully");
                    model.addAttribute("user", new User());
                    model.addAttribute("title", "Sign up success");
                }
            }
        }*/
     /*   return "/registration";
    
}
}

*/