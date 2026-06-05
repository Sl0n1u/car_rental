package wspa.zbiciak.car_rental.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "cars")
public @Data class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    private String engineDetails;

    @Column(nullable = false)
    private BigDecimal pricePerDay;

}