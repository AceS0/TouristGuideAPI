package tourism.repository;

import org.springframework.stereotype.Repository;
import tourism.model.TouristAttraction;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    private final List<TouristAttraction> attractions = new ArrayList<>();

    public TouristRepository(){

        attractions.add(new TouristAttraction("Den Lille Havfrue","Ikonisk statue i København"));
        attractions.add(new TouristAttraction("Legoland","Temapark i Billund"));
        attractions.add(new TouristAttraction("Tivoli","Forlystelsespark i København"));
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
