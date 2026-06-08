package wspa.zbiciak.car_rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import wspa.zbiciak.car_rental.model.AppUser;
import wspa.zbiciak.car_rental.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Szuka pliku login.html w templates
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        // Przekazujemy pusty obiekt, żeby Thymeleaf miał z czym powiązać formularz
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("user") AppUser user, BindingResult bindingResult, Model model) {
        // 1. Sprawdzamy czy formularz spełnia wymogi (np. poprawność emaila)
        if (bindingResult.hasErrors()) {
            return "register";
        }
        
        // 2. Logika zapisu
        boolean isRegistered = userService.registerNewUser(user);
        if (!isRegistered) {
            model.addAttribute("error", "Użytkownik o takiej nazwie lub emailu już istnieje!");
            return "register";
        }
        
        return "redirect:/login?registered";
    }
}