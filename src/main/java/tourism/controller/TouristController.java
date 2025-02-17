package tourism.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
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
    public ResponseEntity<List<TouristAttraction>> getAllAttractions(){
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        return ResponseEntity.ok(attractions);
    }

    //Henter en attraktion baseret på navn. (Get metoden)
    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getAttractionByName(@PathVariable String name){
        TouristAttraction attraction = touristService.getAttractionByName(name);
        return (attraction != null) ? ResponseEntity.ok(attraction) : ResponseEntity.notFound().build();
    }

    //Tilføjer en ny attraktion. (Post metoden)
    @PostMapping("/add")
    public ResponseEntity<String> addAttraction(@RequestBody TouristAttraction attraction){
        touristService.addAttraction(attraction);
        return ResponseEntity.ok("Attraktionen blev tilføjet.");
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
