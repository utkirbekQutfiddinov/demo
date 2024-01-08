package uz.utkirbek.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.TrainerDao;
import uz.utkirbek.model.Trainer;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TrainerDaoImpl implements TrainerDao {
    private JdbcTemplate jdbcTemplate;

    public TrainerDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Trainer> getAll() {
        String sql = "SELECT * FROM trainers";
        return jdbcTemplate.query(sql, new TrainerRowMapper());
    }

    public Trainer getOne(Integer id) {
        String sql = "SELECT * FROM trainers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new TrainerRowMapper());
    }

    public void add(Trainer bean) throws Exception {
        String sql = "INSERT INTO trainers (userId, specialization) VALUES (?, ?)";
        jdbcTemplate.update(sql, bean.getUserId(), bean.getSpecialization());
    }

    public void update(Trainer bean) throws Exception {
        String sql = "update trainers set userId=?, specialization=? where id=?";
        jdbcTemplate.update(sql, bean.getUserId(),bean.getSpecialization(),bean.getId());
    }

    public void delete(Integer id) throws Exception {
        String sql = "DELETE FROM trainers WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }

    private static final class TrainerRowMapper implements RowMapper<Trainer> {

        public Trainer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Trainer trainer = new Trainer();
            trainer.setId(rs.getInt("id"));
            trainer.setUserId(rs.getInt("userId"));
            trainer.setSpecialization(rs.getString("specialization"));
            return trainer;
        }
    }
}
