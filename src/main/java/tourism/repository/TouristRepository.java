package tourism.repository;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tourism.model.TouristAttraction;

import java.util.List;

@Repository
public class TouristRepository {
    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //Tilføjer en attraktion til Arraylisten. (Create funktion)
    public boolean addAttraction(TouristAttraction attraction) {
        try {
            // Indsæt attraktionen
            String sql = "INSERT INTO tourist_attractions (name, description, city) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, attraction.getName(), attraction.getDescription(), attraction.getCity());

            // Hent ID på den nyoprettede attraktion
            String idSql = "SELECT id FROM tourist_attractions WHERE name = ?";
            Integer attractionId = jdbcTemplate.queryForObject(idSql, Integer.class, attraction.getName());

            if (attractionId != null) {
                // Indsæt tags
                String tagSql = "INSERT INTO attraction_tags (attraction_id, tag) VALUES (?, ?)";
                for (String tag : attraction.getTags()) {
                    jdbcTemplate.update(tagSql, attractionId, tag);
                }
            }
            return true;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

    //Lister alle attraktioner. (Read funktion)
    public List<TouristAttraction> getAllAttractions() {
        String sql = "SELECT * FROM tourist_attractions";
        return jdbcTemplate.query(sql,mapAttraction());
    }

    //Henter en attraktion ud fra navnet. (Read funktion)
    public TouristAttraction getAttractionByName(String name){
        try {
            String sql = "SELECT * FROM tourist_attractions WHERE name = ?";
            TouristAttraction attraction = jdbcTemplate.queryForObject(sql, mapAttraction(), name);

            if (attraction != null) {
                String tagSql = "SELECT tag FROM attraction_tags WHERE attraction_id = (SELECT id FROM tourist_attractions WHERE name = ?)";
                List<String> tags = jdbcTemplate.queryForList(tagSql, String.class, name);
                attraction.setTags(tags);
            }
            return attraction;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //Opdaterer en attraktion hvis der opstår eksempelvis ny information eller mangler rettelser. (Update funktion)
    public boolean updateAttraction(TouristAttraction updatedAttraction) {
        String sql = "UPDATE tourist_attractions SET description = ?, city = ? WHERE name = ?";
        int rowsAffected = jdbcTemplate.update(sql, updatedAttraction.getDescription(), updatedAttraction.getCity(), updatedAttraction.getName());

        if (rowsAffected > 0) {
            // Slet de gamle tags
            String deleteTagsSql = "DELETE FROM attraction_tags WHERE attraction_id = (SELECT id FROM tourist_attractions WHERE name = ?)";
            jdbcTemplate.update(deleteTagsSql, updatedAttraction.getName());

            // Tilføj de nye tags
            String insertTagSql = "INSERT INTO attraction_tags (attraction_id, tag) VALUES ((SELECT id FROM tourist_attractions WHERE name = ?), ?)";
            for (String tag : updatedAttraction.getTags()) {
                jdbcTemplate.update(insertTagSql, updatedAttraction.getName(), tag);
            }
            return true;
        }
        return false;
    }

    //Sletter en attraktion. (Delete funktion)
    public boolean deleteAttraction(String name){
        String sql = "DELETE FROM tourist_attractions WHERE name = ?";
        int rowsAffected = jdbcTemplate.update(sql, name);
        return rowsAffected > 0;
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
