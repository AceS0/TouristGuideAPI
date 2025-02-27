package tourism.repository;

import org.springframework.stereotype.Repository;
import tourism.model.TouristAttraction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class TouristRepository {
    private final List<TouristAttraction> attractions
            = new ArrayList<>(List.of(
            new TouristAttraction("CopenHill",
                    "En urban skibakke og aktivitetspark på taget af et forbrændingsanlæg, hvor man kan stå på ski, løbe eller nyde panoramaudsigten over København.",
                    "København",
                    List.of("Sport", "Udendørs", "Eventyr", "Udsigt")
            ),
            new TouristAttraction("Reffen Street Food",
                    "En kreativ mad- og kulturhub i København, hvor unge kan smage internationale retter, lytte til livemusik og opleve en afslappet street food-atmosfære.",
                    "København",
                    List.of("Mad", "Street Food", "Kultur", "Socialt")
            ),
            new TouristAttraction("FÆNGSLET",
                    "Et tidligere fængsel i Horsens, omdannet til museum, koncertsted og eventlokale, hvor man kan opleve autentiske celler, spændende historie og escape room-udfordringer.",
                    "Horsens",
                    List.of("Historie", "Museum", "Escape Room", "Koncerter")
            ),
            new TouristAttraction("Aarhus Festuge",
                    "En årlig kulturfestival i Aarhus, der byder på alt fra koncerter og kunstinstallationer til teater og debatter, skabt for at samle unge og kreative sjæle.",
                    "Aarhus",
                    List.of("Festival", "Kultur", "Musik", "Kunst")
            )
    ));

    public TouristRepository(){
    }

    //Tilføjer en attraktion til Arraylisten. (Create funktion)
    public void addAttraction(TouristAttraction attraction){
        attractions.add(attraction);
    }

    //Lister alle attraktioner. (Read funktion)
    public List<TouristAttraction> getAllAttractions() {
        return new ArrayList<>(attractions);
    }

    //Henter en attraktion ud fra navnet. (Read funktion)
    public TouristAttraction getAttractionByName(String name){
        for (TouristAttraction attraction : attractions){
            if (attraction.getName().equalsIgnoreCase(name)){
                return attraction;
            }
        }
        return null;
    }

    //Opdaterer en attraktion hvis der opstår eksempelvis ny information eller mangler rettelser. (Update funktion)
    public boolean updateAttraction(String name, String updateDesc){
        for (TouristAttraction attraction : attractions){
            if (attraction.getName().equalsIgnoreCase(name)){
                attraction.setDescription(updateDesc);
                return true;
            }
        }
        return false;
    }

    //Sletter en attraktion. (Delete funktion)
    public boolean deleteAttraction(String name){
        for (int i = 0; i < attractions.size(); i++){
            if(attractions.get(i).getName().equalsIgnoreCase(name)){
                attractions.remove(i);
                return true;
            }

        }
        return false;
    }

    //Henter alle byerne i de forskellige attraktioner og gøre det til valgmuligheder.
    public List<String> getCities() {
        Set<String> uniqueCities = new HashSet<>();
        for (TouristAttraction attraction : attractions) {
            uniqueCities.add(attraction.getCity());
        }
        return new ArrayList<>(uniqueCities);
    }

    //Samme som byerne men bare i forhold til tags.
    public List<String> getTags() {
        Set<String> uniqueTags = new HashSet<>();
        for(TouristAttraction attraction : attractions) {
            uniqueTags.addAll(attraction.getTags());
        }
        return new ArrayList<>(uniqueTags);
    }
}
