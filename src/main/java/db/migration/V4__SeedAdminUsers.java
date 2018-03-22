package db.migration;

import com.sysc4806.project.models.UserEntity;
import com.sysc4806.project.models.UserRole;
import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class V4__SeedAdminUsers implements SpringJdbcMigration{

    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UserEntity michael = new UserEntity("Michael");
        UserEntity reid = new UserEntity("Reid");
        UserEntity alex = new UserEntity("Alex");

        michael.setAuthorizationRole(UserRole.ADMIN);
        reid.setAuthorizationRole(UserRole.ADMIN);
        alex.setAuthorizationRole(UserRole.ADMIN);

        michael.setPassword(passwordEncoder.encode("passw0rd"));
        reid.setPassword(passwordEncoder.encode("passw0rd"));
        alex.setPassword(passwordEncoder.encode("passw0rd"));

        insertUser(michael, jdbcTemplate);
        insertUser(reid, jdbcTemplate);
        insertUser(alex, jdbcTemplate);
    }

    private void insertUser(UserEntity userEntity, JdbcTemplate jdbcTemplate)
    {
        jdbcTemplate.execute("INSERT INTO user_entity (password, username, authorization_role) VALUES ('" + userEntity.getPassword() + "', '" + userEntity.getUsername() + "', " + userEntity.getAuthorizationRole().ordinal() + ") ON CONFLICT DO NOTHING");
    }
}
