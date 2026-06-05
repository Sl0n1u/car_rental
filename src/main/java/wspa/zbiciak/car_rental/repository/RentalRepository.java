package wspa.zbiciak.car_rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wspa.zbiciak.car_rental.model.Rental;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    // Spring Data JPA automatycznie wygeneruje zapytanie filtrujące po loginie użytkownika
    List<Rental> findByAppUserUsername(String username);
}