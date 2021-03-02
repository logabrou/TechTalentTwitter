package com.tts.TechTalentTwitter.controller;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.TechTalentTwitter.model.User;
import com.tts.TechTalentTwitter.service.UserService;

@Controller
public class AuthorizationController {
	
	@Autowired
    private UserService userService;

	
//	return login page 
    @GetMapping(value="/login")
    public String login(){
        return "login";
    }
    
    
    
//  binds a user object named "user"  to the forms and fields in html page registration
    @GetMapping(value="/signup")
    public String registration(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

//  New user is created from the post mapping and validated against annotations in the
//  user class. Binding result holds validation results and errors. If user already 
//  exists reject all values in held in the binding result. If error free, create a   
//  new user and bind attributes to "user" and show html registration page. Post mapping
//  is called from the form submit button.
    @PostMapping(value = "/signup")
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
        User userExists = userService.findByUsername(user.getUsername());
        if (userExists != null) {
            bindingResult.rejectValue("username", "error.user", "Username is already taken");
        }
        if (!bindingResult.hasErrors()) {
            userService.saveNewUser(user);
            model.addAttribute("success", "Sign up successful!");
            model.addAttribute("user", new User());
        }
        return "registration";
    }

}
