package wspa.zbiciak.car_rental.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wspa.zbiciak.car_rental.model.AppUser;
import wspa.zbiciak.car_rental.model.Car;
import wspa.zbiciak.car_rental.model.Rental;
import wspa.zbiciak.car_rental.repository.CarRepository;
import wspa.zbiciak.car_rental.repository.RentalRepository;
import wspa.zbiciak.car_rental.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/rentals")
public class RentalWebController {

    private final CarRepository carRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    public RentalWebController(CarRepository carRepository, RentalRepository rentalRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }

    // 1. Formularz wyboru dat dla konkretnego auta
    @GetMapping("/car/{carId}")
    public String showRentForm(@PathVariable Long carId, 
                               @RequestParam(required = false) String startDate, 
                               @RequestParam(required = false) String endDate, 
                               Model model) {
        
        // Zabezpieczenie na wypadek, gdyby ktoś wpisał adres z palca bez dat
        if (startDate == null || endDate == null) {
            return "redirect:/cars"; 
        }

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Niepoprawne ID auta: " + carId));

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        // Wyliczamy dni i cenę już tutaj
        long days = ChronoUnit.DAYS.between(start, end);
        if (days <= 0) days = 1;
        BigDecimal totalPrice = car.getPricePerDay().multiply(BigDecimal.valueOf(days));

        // Przekazujemy wszystko do nowego widoku podsumowania
        model.addAttribute("car", car);
        model.addAttribute("startDate", start);
        model.addAttribute("endDate", end);
        model.addAttribute("days", days);
        model.addAttribute("totalPrice", totalPrice);

        return "rent-form";
    }

    // 2. Przetworzenie rezerwacji
    @PostMapping("/car/{carId}")
    public String processRental(@PathVariable Long carId,
                                @RequestParam("startDate") String startStr,
                                @RequestParam("endDate") String endStr,
                                Model model) {
        
        Car car = carRepository.findById(carId).orElseThrow();
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser user = userRepository.findByUsername(currentUsername).orElseThrow();

        LocalDate start = LocalDate.parse(startStr);
        LocalDate end = LocalDate.parse(endStr);

        // 1. Walidacja logiczna samych dat (czy koniec nie jest przed początkiem)
        if (end.isBefore(start) || start.isBefore(LocalDate.now())) {
            model.addAttribute("car", car);
            model.addAttribute("error", "Wybrano niepoprawne daty!");
            return "rent-form";
        }

        // 2. NOWOŚĆ: Walidacja dostępności auta w bazie danych
        boolean isOccupied = rentalRepository.existsOverlappingRental(carId, start, end);
        if (isOccupied) {
            model.addAttribute("car", car);
            model.addAttribute("error", "Wybrany pojazd jest już zarezerwowany w tym terminie! Wybierz inne daty.");
            return "rent-form";
        }

        // 3. Obliczenia i zapis (bez zmian)
        long days = ChronoUnit.DAYS.between(start, end);
        if (days <= 0) days = 1;

        BigDecimal totalPrice = car.getPricePerDay().multiply(BigDecimal.valueOf(days));

        Rental rental = new Rental();
        rental.setCar(car);
        rental.setAppUser(user);
        rental.setStartDate(start);
        rental.setEndDate(end);
        rental.setTotalPrice(totalPrice);

        rentalRepository.save(rental);

        return "redirect:/rentals/my";
    }

    // 3. Profil użytkownika - lista jego wypożyczeń
    @GetMapping("/my")
    public String showMyRentals(Model model) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Rental> myRentals = rentalRepository.findByAppUserUsername(currentUsername);
        
        model.addAttribute("rentals", myRentals);
        return "my-rentals";
    }
}