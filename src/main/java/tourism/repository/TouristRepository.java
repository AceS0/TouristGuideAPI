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
        TouristAttraction attraction = jdbcTemplate.queryForObject(sql, mapAttraction(), name);

        if (attraction != null) {
            String tagSql = "SELECT tag FROM attraction_tags WHERE attraction_id = (SELECT id FROM tourist_attractions WHERE name = ?)";
            List<String> tags = jdbcTemplate.queryForList(tagSql, String.class, name);
            attraction.setTags(tags);
        }
        return attraction;
    }

    //Opdaterer en attraktion hvis der opstår eksempelvis ny information eller mangler rettelser. (Update funktion)
    public boolean updateAttraction(String name, String updateDesc) {
        String sql = "UPDATE tourist_attractions SET description = ? WHERE name = ?";
        int rowsAffected = jdbcTemplate.update(sql, updateDesc, name);
        return rowsAffected > 0;
    }

    //Sletter en attraktion. (Delete funktion)
    public boolean deleteAttraction(String name){
        String sql = "DELETE FROM tourist_attractions WHERE name = ?";
        jdbcTemplate.update(sql, name);
        return false;
    }

    //Henter alle byerne i de forskellige attraktioner og gøre det til valgmuligheder.
    public List<String> getCities() {
        String sql = "SELECT DISTINCT city FROM tourist_attractions";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    //Samme som byerne men bare i forhold til tags.
    public List<String> getTags() {
        String sql = "SELECT DISTINCT tag FROM attraction_tags";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    private RowMapper<TouristAttraction> mapAttraction() {
        return (rs, rowNum) -> new TouristAttraction(
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("city"),
                List.of() // Tags håndteres separat
        );
    }
}
