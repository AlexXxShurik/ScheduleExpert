package config;

import database.DatabaseGet;
import org.app.*;
import org.app.tabs.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public TableSchedule TableSchedule() {
        return new TableSchedule();
    }

    @Bean
    public MenuBarConfig menuBarConfig() {
        return new MenuBarConfig();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/mydatabase");
        dataSource.setUsername("user");
        dataSource.setPassword("password");
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DatabaseGet databaseGet(JdbcTemplate jdbcTemplate) {
        return new DatabaseGet(jdbcTemplate);
    }

    @Bean
    public ScheduleDisplay scheduleDisplay(TableSchedule tableSchedule) {
        return new ScheduleDisplay(tableSchedule);
    }

    @Bean
    public AddPeopleTab addPeopleTab() {
        return new AddPeopleTab();
    }

    @Bean
    public ShiftSettingsTab shiftSettingsTab(DatabaseGet databaseGet) {
        return new ShiftSettingsTab(databaseGet);
    }
}
