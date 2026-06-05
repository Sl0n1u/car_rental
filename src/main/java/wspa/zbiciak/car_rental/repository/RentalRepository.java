package wspa.zbiciak.car_rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wspa.zbiciak.car_rental.model.Rental;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    
    List<Rental> findByAppUserUsername(String username);

    // Nowa metoda do sprawdzania konfliktów terminów
    @Query("SELECT COUNT(r) > 0 FROM Rental r WHERE r.car.id = :carId " +
           "AND r.startDate <= :endDate AND r.endDate >= :startDate")
    boolean existsOverlappingRental(@Param("carId") Long carId, 
                                    @Param("startDate") LocalDate startDate, 
                                    @Param("endDate") LocalDate endDate);
}