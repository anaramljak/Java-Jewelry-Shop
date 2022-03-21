package app.Controllers;

import app.DTO.UserRegistrationDTO;
import app.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new UserRegistrationDTO());
        return "register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user")UserRegistrationDTO registrationDTO){
        return userService.save(registrationDTO);
    }
}
