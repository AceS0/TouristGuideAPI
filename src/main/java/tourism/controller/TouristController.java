package tourism.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;
import tourism.service.TouristService;

import java.util.List;

@Controller
@RequestMapping("/attractions")
public class TouristController {
    private final TouristService touristService;


    public TouristController(TouristService touristService){
        this.touristService = touristService;
    }

    //Henter hele listen for attraktioner. (Get metoden)
    @GetMapping
    public String viewAttractions(Model model){
        model.addAttribute("attractions", touristService.getAllAttractions());
        return "attractionList";
    }

    //Henter en attraktion baseret på navn. (Get metoden)
    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getAttractionByName(@PathVariable String name){
        TouristAttraction attraction = touristService.getAttractionByName(name);
        return (attraction != null) ? ResponseEntity.ok(attraction) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{name}/tags")
    public String tags(@PathVariable String name, Model model){
        TouristAttraction attraction = touristService.getAttractionByName(name);
        if (attraction != null){
            model.addAttribute("attraction",attraction);
            return "tags";
        } else {
            return "error";
        }
    }

    @GetMapping("/add")
    public String showAddAttractionForm(Model model){
        TouristAttraction attraction = new TouristAttraction();

        // Tilføjer det tomme objekt til modellen
        model.addAttribute("attraction", attraction);

        // Henter og tilføjer lister af byer og tags til modellen
        model.addAttribute("cities", touristService.);
        model.addAttribute("availableTags", repository.getTags());

        return "addAttraction";
    }

    //Tilføjer en ny attraktion. (Post metoden)
    @PostMapping("/add")
    public String addAttraction(@RequestParam String name, @RequestParam String description, @RequestParam String city, @RequestParam List<String> tags){
        touristService.addAttraction(new TouristAttraction(name,description,city,tags));
        return "redirect:/attractions";
    }

    //Opdaterer en attraktion. (Post metoden)
    @PostMapping("/update")
    public ResponseEntity<String> updateAttraction(@RequestBody TouristAttraction attraction) {
        boolean updated = touristService.updateAttraction(attraction.getName(), attraction.getDescription());
        return updated ? ResponseEntity.ok("Attraktionen blev opdateret.") : ResponseEntity.notFound().build();
    }

    //Sletter en attraktion. (Post metoden)
    @PostMapping("/delete/{name}")
    public ResponseEntity<String> deleteAttraction(@PathVariable String name){
        boolean deleted = touristService.deleteAttraction(name);
        return deleted ? ResponseEntity.ok("Attraktionen blev slettet.") : ResponseEntity.notFound().build();
    }
}
