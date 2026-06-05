package wspa.zbiciak.car_rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wspa.zbiciak.car_rental.model.AppUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    // Ta metoda będzie krytyczna przy logowaniu, żeby sprawdzić, czy użytkownik istnieje
    Optional<AppUser> findByUsername(String username);
}