package wspa.zbiciak.car_rental;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wspa.zbiciak.car_rental.model.AppUser;
import wspa.zbiciak.car_rental.model.Car;
import wspa.zbiciak.car_rental.repository.CarRepository;
import wspa.zbiciak.car_rental.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, CarRepository carRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        
        if (userRepository.findByUsername("admin").isEmpty()) {
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@autex.pl");
            admin.setPhoneNumber("000000000");
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
        }

        if (carRepository.count() == 0) {
            Car porsche = new Car();
            porsche.setBrand("Porsche");
            porsche.setModel("911 Carrera S");
            porsche.setEngineDetails("3.0 B6 Twin-Turbo 450 KM");
            porsche.setPricePerDay(new BigDecimal("2500.00"));

            Car ferrari = new Car();
            ferrari.setBrand("Ferrari");
            ferrari.setModel("F8 Tributo");
            ferrari.setEngineDetails("3.9 V8 Twin-Turbo 720 KM");
            ferrari.setPricePerDay(new BigDecimal("4500.00"));

            Car lamborghini = new Car();
            lamborghini.setBrand("Lamborghini");
            lamborghini.setModel("Huracán EVO");
            lamborghini.setEngineDetails("5.2 V10 640 KM");
            lamborghini.setPricePerDay(new BigDecimal("4000.00"));

            Car aston = new Car();
            aston.setBrand("Aston Martin");
            aston.setModel("DB11");
            aston.setEngineDetails("4.0 V8 Twin-Turbo 510 KM");
            aston.setPricePerDay(new BigDecimal("3000.00"));

            Car bentley = new Car();
            bentley.setBrand("Bentley");
            bentley.setModel("Continental GT");
            bentley.setEngineDetails("6.0 W12 Twin-Turbo 635 KM");
            bentley.setPricePerDay(new BigDecimal("3500.00"));

            carRepository.saveAll(List.of(porsche, ferrari, lamborghini, aston, bentley));
        }
    }
}