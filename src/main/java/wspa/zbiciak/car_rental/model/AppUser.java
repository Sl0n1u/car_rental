package wspa.zbiciak.car_rental.model;

import jakarta.persistence.*;
import lombok.Data;

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

    @Column(nullable = false)
    private String role;

}