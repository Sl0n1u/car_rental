package wspa.zbiciak.car_rental.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wspa.zbiciak.car_rental.model.AppUser;
import wspa.zbiciak.car_rental.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerNewUser(AppUser user) {
        // Sprawdzamy, czy użytkownik o takiej nazwie już nie istnieje
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return false; // Rejestracja nieudana
        }

        // Haszowanie hasła algorytmem BCrypt przed zapisem do bazy danych
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Nadajemy domyślną rolę
        user.setRole("ROLE_USER");
        
        userRepository.save(user);
        return true;
    }
}