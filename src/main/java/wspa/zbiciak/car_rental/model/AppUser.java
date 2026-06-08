package wspa.zbiciak.car_rental.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users") // Krytyczne dla PostgreSQL!
public @Data class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Email jest wymagany")
    @Email(message = "Podaj poprawny adres email")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Numer telefonu jest wymagany")
    @Pattern(regexp = "^\\+?[0-9\\s]{9,15}$", message = "Podaj poprawny numer telefonu (od 9 do 15 cyfr)")
    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String role;

}