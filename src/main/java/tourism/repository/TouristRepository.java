package tourism.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tourism.model.TouristAttraction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class TouristRepository {
    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //Tilføjer en attraktion til Arraylisten. (Create funktion)
    public void addAttraction(TouristAttraction attraction){
        String sql = "INSERT INTO tourist_attractions (name, description, city) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, attraction.getName(), attraction.getDescription(), attraction.getCity());
    }

    //Lister alle attraktioner. (Read funktion)
    public List<TouristAttraction> getAllAttractions() {
        String sql = "SELECT * FROM tourist_attractions";
        return jdbcTemplate.query(sql,mapAttraction());
    }

    //Henter en attraktion ud fra navnet. (Read funktion)
    public TouristAttraction getAttractionByName(String name){
        String sql = "SELECT * FROM tourist_attractions WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, mapAttraction(), name);
    }

    //Opdaterer en attraktion hvis der opstår eksempelvis ny information eller mangler rettelser. (Update funktion)
   /* public boolean updateAttraction(String name, String updateDesc){
        for (TouristAttraction attraction : attractions){
            if (attraction.getName().equalsIgnoreCase(name)){
                attraction.setDescription(updateDesc);
                return true;
            }
        }
        return false;
    }*/

    //Sletter en attraktion. (Delete funktion)
    public boolean deleteAttraction(String name){
        String sql = "DELETE FROM tourist_attractions WHERE name = ?";
        jdbcTemplate.update(sql, name);
        return false;
    }

    //Henter alle byerne i de forskellige attraktioner og gøre det til valgmuligheder.
   /* public List<String> getCities() {
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
    }*/

    private RowMapper<TouristAttraction> mapAttraction() {
        return (rs, rowNum) -> new TouristAttraction(
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("city"),
                List.of() // Tags håndteres separat
        );
    }
}
