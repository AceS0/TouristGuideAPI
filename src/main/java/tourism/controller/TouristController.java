package tourism.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;


@Controller
@RequestMapping("")
public class TouristController {
    private final TouristService touristService;


    public TouristController(TouristService touristService){
        this.touristService = touristService;
    }

    //Henter hele listen for attraktioner. (Get metoden)
    @GetMapping("/attractions")
    public String viewAttractions(Model model){
        model.addAttribute("attractions", touristService.getAllAttractions());
        return "attractionList";
    }

    //Henter en attraktion baseret på navn. (Get metoden)
    @GetMapping("/attractions/{name}")
    public ResponseEntity<TouristAttraction> getAttractionByName(@PathVariable String name){
        TouristAttraction attraction = touristService.getAttractionByName(name);
        return (attraction != null) ? ResponseEntity.ok(attraction) : ResponseEntity.notFound().build();
    }

    //Henter tags for den specifikke attraktion. (Get metoden)
    @GetMapping("/attractions/{name}/tags")
    public String tags(@PathVariable String name, Model model){
        TouristAttraction attraction = touristService.getAttractionByName(name);
        if (attraction != null){
            model.addAttribute("attraction",attraction);
            return "tags";
        } else {
            return null;
        }
    }

    @GetMapping("/attractions/add")
    public String showAddAttractionForm(Model model){
        TouristAttraction attraction = new TouristAttraction();
        model.addAttribute("attraction", attraction);

        //Henter og tilføjer lister af byer og tags til modellen
        model.addAttribute("cities", touristService.getAllCities());
        model.addAttribute("availableTags", touristService.getAllTags());

        return "add-attraction";
    }

    //Tilføjer en ny attraktion. (Post metoden)
    @PostMapping("/attractions/save")
    public String saveAttraction(@ModelAttribute TouristAttraction attraction) {
        touristService.addAttraction(attraction);
        return "redirect:/attractions";
    }

    //Redigerer en attraktion tilknyttet opdaterer funktionen. (Get metoden)
    @GetMapping("/attractions/{name}/edit")
    public String editAttraction(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.getAttractionByName(name);
        if (attraction != null) {
            // Lægger attraktionen på modellen, så Thymeleaf kan udfylde felterne
            model.addAttribute("attraction", attraction);

            // Hvis du fx har en dropdown for byer:
            model.addAttribute("cities", touristService.getAllCities());

            // Hvis du fx har en liste af tags, man kan vælge:
            model.addAttribute("availableTags", touristService.getAllTags());

            return "edit-attraction";  // Henviser til Thymeleaf-skabelonen "edit-attraction.html"
        } else {
            return null; // Hvis attraktionen ikke findes, kan du vise en fejl-HTML eller lignende
        }
    }

    //Opdaterer en attraktion. (Post metoden)
    @PostMapping("/attractions/update")
    public String updateAttraction(@ModelAttribute TouristAttraction updatedAttraction) {
        //Her henter du den eksisterende attraktion (baseret på navn)
        TouristAttraction existing = touristService.getAttractionByName(updatedAttraction.getName());
        if (existing != null) {

            existing.setDescription(updatedAttraction.getDescription());
            existing.setCity(updatedAttraction.getCity());
            existing.setTags(updatedAttraction.getTags());

            return "redirect:/attractions";
        } else {
            return null;
        }
    }

    //Sletter en attraktion. (Post metoden)
    @PostMapping("/attractions/{name}/delete")
    public String deleteAttraction(@PathVariable String name) {
        boolean deleted = touristService.deleteAttraction(name);
        if (deleted) {
            return "redirect:/attractions";
        } else {
            return null;
        }
    }
}
