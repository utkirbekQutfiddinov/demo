package uz.utkirbek.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.TrainingTypeDao;
import uz.utkirbek.model.TrainingType;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TrainingTypeDaoImpl implements TrainingTypeDao {
    private JdbcTemplate jdbcTemplate;

    public TrainingTypeDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<TrainingType> getAll() {
        String sql = "SELECT * FROM training_types";
        return jdbcTemplate.query(sql, new TrainingTypeRowMapper());
    }

    public TrainingType getOne(Integer id) {
        String sql = "SELECT * FROM training_types WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new TrainingTypeRowMapper());
    }

    public void add(TrainingType bean) throws Exception {
        String sql = "INSERT INTO training_types (name) VALUES (?)";
        jdbcTemplate.update(sql, bean.getName());
    }

    public void update(TrainingType bean) throws Exception {
        String sql = "update training_types set name=? where id=?";
        jdbcTemplate.update(sql, bean.getName(),bean.getId());
    }

    public void delete(Integer id) throws Exception {
        String sql = "DELETE FROM training_types WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static final class TrainingTypeRowMapper implements RowMapper<TrainingType> {

        public TrainingType mapRow(ResultSet rs, int rowNum) throws SQLException {
            TrainingType type = new TrainingType();
            type.setId(rs.getInt("id"));
            type.setName(rs.getString("name"));
            return type;
        }
    }
}
