package org.launchcode.controllers;

import org.launchcode.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by SunCloud
 */
@Controller
@RequestMapping("user")
public class UserController {

    // Request path: /user
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("title", "Users");

        return "user/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddUserForm(Model model) {
        model.addAttribute("title", "Add User");
        model.addAttribute(new User());
        return "user/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid User newUser,
                      Errors errors, String verify) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add User");
            model.addAttribute(newUser);
            return "user/add";
        }

        if (newUser.getPassword().equals(verify) == false) {
            model.addAttribute("title", "Add User");
            model.addAttribute("verifyerror","Passwords must match");
            return "user/add";
        }

        //User.add(newUser);
        return "redirect:";
    }
}
