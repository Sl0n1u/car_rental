package wspa.zbiciak.car_rental.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wspa.zbiciak.car_rental.model.AppUser;
import wspa.zbiciak.car_rental.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika: " + username));

        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword()) // Tu będzie już zahaszowane hasło
                .roles(appUser.getRole().replace("ROLE_", "")) // Spring automatycznie dodaje przedrostek ROLE_
                .build();
    }
}