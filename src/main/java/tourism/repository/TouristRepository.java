package tourism.repository;

import org.springframework.stereotype.Repository;
import tourism.model.TouristAttraction;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    private final List<TouristAttraction> attractions = new ArrayList<>();

    public TouristRepository(){

        attractions.add(new TouristAttraction("CopenHill","En urban skibakke og aktivitetspark på taget af et forbrændingsanlæg, " +
                "hvor man kan stå på ski, løbe eller nyde panoramaudsigten over København."));
        attractions.add(new TouristAttraction("Reffen Street Food","En kreativ mad- og kulturhub i København, hvor unge kan smage internationale retter, " +
                "lytte til livemusik og opleve en afslappet street food-atmosfære."));
        attractions.add(new TouristAttraction("FÆNGSLET","Et tidligere fængsel i Horsens, omdannet til museum," +
                " koncertsted og eventlokale, hvor man kan opleve autentiske celler, spændende historie og escape room-udfordringer"));
        attractions.add(new TouristAttraction("Aarhus Festuge","En årlig kulturfestival i Aarhus, der byder på alt fra koncerter " +
                "og kunstinstallationer til teater og debatter, skabt for at samle unge og kreative sjæle."));
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
}
