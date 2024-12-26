package database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DatabaseGet {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseGet(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ScheduleData> getAllSchedules(LocalDate dateStart, LocalDate dateEnd, String shift) {
        String sql = "WITH previous_records AS (\n" +
                "    SELECT \n" +
                "        e.employee_number,\n" +
                "        e.full_name,\n" +
                "        e.shift,\n" +
                "        s.schedule_pattern,\n" +
                "        MAX(w.date) AS max_date\n" +
                "    FROM \n" +
                "        schedules w\n" +
                "        JOIN employees e ON w.employee_number = e.employee_number\n" +
                "        JOIN schedule_patterns s ON w.schedule_number = s.schedule_number\n" +
                "    WHERE \n" +
                "        w.date < ? AND e.shift LIKE ?\n" +
                "    GROUP BY \n" +
                "        e.employee_number, s.schedule_pattern\n" +
                "    ORDER BY \n" +
                "        max_date DESC\n" +
                "    LIMIT 500\n" +
                ")\n" +
                "\n" +
                "SELECT \n" +
                "    e.employee_number,\n" +
                "    e.full_name,\n" +
                "    e.shift,\n" +
                "    w.date::date AS date,\n" +
                "    w.schedule_number,\n" +
                "    w.shift_number,\n" +
                "    s.schedule_pattern\n" +
                "FROM \n" +
                "    schedules w\n" +
                "    JOIN employees e ON w.employee_number = e.employee_number\n" +
                "    JOIN schedule_patterns s ON w.schedule_number = s.schedule_number\n" +
                "WHERE \n" +
                "    w.date BETWEEN ? AND ? AND e.shift LIKE ?\n" +
                "    OR w.date IN (SELECT max_date FROM previous_records)\n" +
                "ORDER BY \n" +
                "    e.employee_number, w.date;\n";

        return jdbcTemplate.query(
                sql,
                new Object[]{dateStart, shift, dateStart, dateEnd, shift}, // Передача параметров для даты начала и конца
                (rs, rowNum) -> new ScheduleData(
                        rs.getLong("employee_number"),
                        rs.getString("full_name"),
                        rs.getString("shift"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("schedule_number"),
                        rs.getString("shift_number"),
                        rs.getString("schedule_pattern")
                )
        );
    }

    public List<PeopleData> getAllPeople(String shift) {
        String sql = "SELECT * FROM employees WHERE shift = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{shift},
                (rs, rowNum) -> new PeopleData(
                        rs.getInt("employee_number"),
                        rs.getString("full_name"),
                        rs.getString("shift")
                )
        );
    }

    public List<PatternsData> getAllPatterns() {
        String sql = "SELECT * FROM schedule_patterns\n";
        return jdbcTemplate.query(
                sql,
                new Object[]{},
                (rs, rowNum) -> new PatternsData(
                        rs.getInt("id"),
                        rs.getString("schedule_pattern")
                )
        );
    }

    public void updatePersonShift(PeopleData person) {
        String updateQuery = "UPDATE employees SET shift = ? WHERE employee_number = ?";

        jdbcTemplate.update(updateQuery, person.getShift(), person.getEmployee_number());
    }
}
