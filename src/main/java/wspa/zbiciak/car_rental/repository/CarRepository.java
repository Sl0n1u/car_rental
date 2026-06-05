package wspa.zbiciak.car_rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wspa.zbiciak.car_rental.model.Car;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    // Wyciąga tylko te auta, które NIE mają nakładających się rezerwacji w podanym terminie
    @Query("SELECT c FROM Car c WHERE c.id NOT IN (" +
           "SELECT r.car.id FROM Rental r WHERE r.startDate <= :endDate AND r.endDate >= :startDate)")
    List<Car> findAvailableCars(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}