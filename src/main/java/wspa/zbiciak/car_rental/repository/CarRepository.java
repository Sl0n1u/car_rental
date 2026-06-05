package wspa.zbiciak.car_rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wspa.zbiciak.car_rental.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}