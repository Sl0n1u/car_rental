package wspa.zbiciak.car_rental.controller;

import jakarta.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import wspa.zbiciak.car_rental.model.Car;
import wspa.zbiciak.car_rental.repository.CarRepository;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarWebController {

    private final CarRepository carRepository;

    public CarWebController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping
    public String showCarsList(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {
        
        List<Car> cars;

        // Dodatkowe sprawdzenie, czy daty nie są nullami
        if (startDate != null && endDate != null) {
            if (endDate.isBefore(startDate) || startDate.isBefore(LocalDate.now())) {
                model.addAttribute("error", "Wybrano niepoprawny zakres dat.");
                cars = carRepository.findAll();
            } else {
                cars = carRepository.findAvailableCars(startDate, endDate);
                model.addAttribute("searchStartDate", startDate);
                model.addAttribute("searchEndDate", endDate);
            }
        } else {
            cars = carRepository.findAll();
        }

        model.addAttribute("cars", cars);
        return "cars";
    }

    // 1. Wyświetlenie formularza dodawania auta
    @GetMapping("/add")
    public String showAddCarForm(Model model) {
        model.addAttribute("car", new Car());
        return "add-car"; // Szuka pliku add-car.html
    }

    // 2. Przetworzenie danych z formularza wraz z walidacją
    @PostMapping("/add")
    public String processAddCar(@Valid @ModelAttribute("car") Car car, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Jeśli są błędy walidacji (np. puste pola), wracamy do formularza
            return "add-car";
        }
        
        carRepository.save(car);
        return "redirect:/cars"; // Po sukcesie wracamy na listę aut
    }
}