package db.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

public class V2__RemoveAverageRating implements SpringJdbcMigration {
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
        jdbcTemplate.execute("ALTER TABLE product DROP COLUMN average_rating");
        jdbcTemplate.execute("ALTER TABLE product DROP COLUMN number_of_ratings");
        jdbcTemplate.execute("ALTER TABLE product DROP COLUMN rating_total");
    }
}
