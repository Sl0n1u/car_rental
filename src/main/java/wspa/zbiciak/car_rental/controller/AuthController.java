package wspa.zbiciak.car_rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String processRegistration(@ModelAttribute("user") AppUser user, Model model) {
        boolean isRegistered = userService.registerNewUser(user);
        
        if (!isRegistered) {
            model.addAttribute("error", "Użytkownik o takiej nazwie już istnieje!");
            return "register"; // Zwracamy z powrotem do formularza z błędem
        }
        
        // Po sukcesie przekierowujemy na stronę logowania z flagą "registered"
        return "redirect:/login?registered";
    }
}