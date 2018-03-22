package db.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

public class V3__MissingAuth implements SpringJdbcMigration{
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
        jdbcTemplate.execute("ALTER TABLE product ADD COLUMN NAME character varying(255)");
        jdbcTemplate.execute("ALTER TABLE user_entity ADD COLUMN authorization_role INTEGER");
        jdbcTemplate.execute("ALTER TABLE review ADD COLUMN  comment CHARACTER VARYING(2047)");
    }
}
