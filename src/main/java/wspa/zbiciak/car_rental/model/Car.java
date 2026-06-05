package wspa.zbiciak.car_rental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Marka nie może być pusta")
    @Column(nullable = false)
    private String brand;

    @NotBlank(message = "Model nie może być pusty")
    @Column(nullable = false)
    private String model;

    private String engineDetails;

    @NotNull(message = "Cena za dobę jest wymagana")
    @Min(value = 1, message = "Cena za dobę musi wynosić co najmniej 1 PLN")
    @Column(nullable = false)
    private BigDecimal pricePerDay;
}