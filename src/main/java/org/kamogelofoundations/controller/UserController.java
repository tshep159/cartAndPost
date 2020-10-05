package org.kamogelofoundations.controller;

import javax.servlet.http.HttpServletRequest;

import org.kamogelofoundations.dto.User;
import org.kamogelofoundations.exception.UserNotFound;
import org.kamogelofoundations.repository.UserRepository;
import org.kamogelofoundations.service.MailService;
import org.kamogelofoundations.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//import org.imgscalr.Scalr;
/**
 *
 * @author Tshepo
 */
@Controller
public class UserController {

    @Value("${app.user.root}")
    private String userRoot;

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    MailService mailService;
    
    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    

    @RequestMapping(value = "/user/reset-password")
    public String resetPasswordEmail(User user, Model model) {
        model.addAttribute("title", "Reset Password");
        return "/reset-password";
    }

    @RequestMapping(value = "/user/reset-password", method = RequestMethod.POST)
    public String resetPasswordEmailPost(User user, BindingResult result,
            Model model) throws Exception {
        User u = userRepository.findOneByEmail(user.getEmail());
        model.addAttribute("title", "Reset Password");
        if (u == null) {
            result.rejectValue("email", "error.doesntExist",
                    "We could not find this email in our database");
            return "/reset-password";
        } else {
            String resetToken = userService.createResetPasswordToken(u, true);
            mailService.sendResetPassword(user.getEmail(), resetToken);
        }
        return "/reset-password-sent";
    }

    @RequestMapping(value = "/user/reset-password-change")
    public String resetPasswordChange(User user, BindingResult result,
            Model model) {
        User u = userRepository.findOneByToken(user.getToken());
        if (user.getToken().equals("1") || u == null) {
            result.rejectValue("activation", "error.doesntExist",
                    "We could not find this reset password request.");
        } else {
            model.addAttribute("userName", u.getEmail());
        }
        return "/reset-password-change";
    }

    @RequestMapping(value = "/user/reset-password-change", method = RequestMethod.POST)
    public ModelAndView resetPasswordChangePost(User user, BindingResult result) {
        Boolean isChanged =  userService.resetPassword(user);
        if (isChanged) {
            //userService.autoLogin(user.getEmail());
            return new ModelAndView("redirect:/");
        } else {
            return new ModelAndView("/reset-password-change", "error",
                    "Password could not be changed");
        }
    }

    @RequestMapping("/user/activation-send")
    public ModelAndView activationSend(User user) {
        return new ModelAndView("/activation-send");
    }

    @RequestMapping(value = "/user/activation-send", method = RequestMethod.POST)
    public ModelAndView activationSendPost(User user, BindingResult result) {
        User u = userService.resetActivation(user.getEmail());
        if (u != null) {
            // mailService.sendNewActivationRequest(u.getEmail(), u.getToken());
            return new ModelAndView("/user/activation-sent");
        } else {
            result.rejectValue("email", "error.doesntExist",
                    "We could not find this email in our databse");
            return new ModelAndView("/activation-send");
        }
    }

    
    
    
    
    
/*
    @RequestMapping("/user/edit/{id}")
    public String edit(@PathVariable("id") Long id, User user) {
        User u;
        User loggedInUser = userService.getLoggedInUser();
        if (id == 0) {
            id = loggedInUser.getId();
        }
        if (loggedInUser.getId() != id) {
            return "user/premission-denied";
        } else {
            u = userRepository.findOne(id);
            u = loggedInUser;
        }
        user.setId(u.getId());
        user.setUsername(u.getUsername());
        user.setContactNumber(u.getContactNumber());
        user.setProfilePicture(u.getProfilePicture());
        user.setEmail(u.getEmail());

        userService.updateUser(userService.getLoggedInUser().getUsername(),
                user);
        return "/edit";
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String editPost(@Valid User user, BindingResult result) {
        if (result.hasFieldErrors("email")) {
            return "/edit";
        }

        if (userService.getLoggedInUser().getId().equals(user.getId())) {
            // put updated user to session
            userService.getLoggedInUser();
        }

        return "redirect:/user/edit/" + user.getId() + "?updated";
    }

    @RequestMapping(value = "/user/reset-password")
    public String resetPasswordEmail(User user, Model model) {
        model.addAttribute("title", "Reset Password");
        return "/reset-password";
    }

    @RequestMapping(value = "/user/reset-password", method = RequestMethod.POST)
    public String resetPasswordEmailPost(User user, BindingResult result,
            Model model) {
        User u = userRepository.findOneByEmail(user.getEmail());
        model.addAttribute("title", "Reset Password");
        if (u == null) {
            result.rejectValue("email", "error.doesntExist",
                    "We could not find this email in our database");
            return "/reset-password";
        } else {
            String resetToken = userService.createResetPasswordToken(u, true);
            // mailService.sendResetPassword(user.getEmail(), resetToken);
        }
        return "/reset-password-sent";
    }

    @RequestMapping(value = "/user/reset-password-change")
    public String resetPasswordChange(User user, BindingResult result,
            Model model) {
        User u = userRepository.findOneByToken(user.getToken());
        if (user.getToken().equals("1") || u == null) {
            result.rejectValue("activation", "error.doesntExist",
                    "We could not find this reset password request.");
        } else {
            model.addAttribute("userName", u.getUsername());
        }
        return "/reset-password-change";
    }

    @RequestMapping(value = "/user/reset-password-change", method = RequestMethod.POST)
    public ModelAndView resetPasswordChangePost(User user, BindingResult result) {
        Boolean isChanged = userService.resetPassword(user);
        if (isChanged) {
            userService.autoLogin(user.getUsername());
            return new ModelAndView("redirect:/");
        } else {
            return new ModelAndView("/reset-password-change", "error",
                    "Password could not be changed");
        }
    }

    @RequestMapping("/user/activation-send")
    public ModelAndView activationSend(User user) {
        return new ModelAndView("/activation-send");
    }

    @RequestMapping(value = "/user/activation-send", method = RequestMethod.POST)
    public ModelAndView activationSendPost(User user, BindingResult result) {
        User u = userService.resetActivation(user.getEmail());
        if (u != null) {
            // mailService.sendNewActivationRequest(u.getEmail(), u.getToken());
            return new ModelAndView("/user/activation-sent");
        } else {
            result.rejectValue("email", "error.doesntExist",
                    "We could not find this email in our databse");
            return new ModelAndView("/activation-send");
        }
    }

    @RequestMapping(value = "/user/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            Principal p) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        String fileName = formatter.format(Calendar.getInstance().getTime())
                + "_thumbnail.jpg";
        // User user = userService.getLoggedInUser();
        Optional<User> user = userService.findByUsername(p.getName());
        if (!file.isEmpty()) {
            try {
                String saveDirectory = userRoot + File.separator + user.get()
                        + File.separator;
                File test = new File(saveDirectory);
                if (!test.exists()) {
                    test.mkdirs();
                }

                byte[] bytes = file.getBytes();

                ByteArrayInputStream imageInputStream = new ByteArrayInputStream(
                        bytes);
                BufferedImage image = ImageIO.read(imageInputStream);
                // BufferedImage thumbnail = Scalr.resize(image, 200);

                File thumbnailOut = new File(saveDirectory + fileName);
                // /ImageIO.write(thumbnail, "png", thumbnailOut);

                userService.updateProfilePicture(user.get(), fileName);
                // userService.getLoggedInUser(true); //Force refresh of cached
                // User
                System.out.println("Image Saved::: " + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/user/edit/" + user.get();
    }

    @RequestMapping(value = "/user/profile-picture", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] profilePicture() throws IOException {
        User u = userService.getLoggedInUser();
        String profilePicture = userRoot + File.separator + u.getId()
                + File.separator + u.getProfilePicture();
        if (new File(profilePicture).exists()) {
            return IOUtils.toByteArray(new FileInputStream(profilePicture));
        } else {
            return null;
        }
    }*/

    @ExceptionHandler(UserNotFound.class)
    public ModelAndView handleError(HttpServletRequest req,
            UserNotFound exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", exception.getMessage());
        mav.addObject("exception", exception);
        mav.addObject("title", "No user available!");
        mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());

        mav.addObject("errorTitle", "Sorry please try again");

        mav.addObject("errorDescription",
                "The user you are looking for is not yet registered with us!");
        mav.setViewName("/userNotFound");
        return mav;
    }
}
