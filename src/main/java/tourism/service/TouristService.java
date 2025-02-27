package tourism.service;

import org.springframework.stereotype.Service;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository){
        this.touristRepository = touristRepository;
    }

    //Tilføjer en attraktion til Arraylisten igennem touristRepository. (Create funktion)
    public void addAttraction(TouristAttraction attraction){
        touristRepository.addAttraction(attraction);
    }

    //Lister alle attraktioner igennem touristRepository. (Read funktion)
    public List<TouristAttraction> getAllAttractions(){
        return touristRepository.getAllAttractions();
    }

    //Henter en attraktion ud fra navnet igennem touristRepository. (Read funktion)
    public TouristAttraction getAttractionByName(String name){
        return touristRepository.getAttractionByName(name);
    }

    //Opdaterer en attraktion igennem touristRepository. (Update funktion)
    public boolean updateAttraction(String name, String updateDesc){
        return touristRepository.updateAttraction(name,updateDesc);
    }

    //Sletter en attraktion igennem touristRepository. (Delete funktion)
    public boolean deleteAttraction(String name){
        return touristRepository.deleteAttraction(name);
    }

    //Henter alle byer og returnerer dem.
    public List<String> getAllCities(){
        return touristRepository.getCities();
    }

    //Henter alle tags og returnerer dem.
    public List<String> getAllTags(){
        return touristRepository.getTags();
    }

}
