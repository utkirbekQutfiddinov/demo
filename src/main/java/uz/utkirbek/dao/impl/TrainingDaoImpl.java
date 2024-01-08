package uz.utkirbek.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.TrainingDao;
import uz.utkirbek.model.Training;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    private JdbcTemplate jdbcTemplate;

    public TrainingDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Training> getAll() {
        String sql = "SELECT * FROM trainings";
        return jdbcTemplate.query(sql, new TrainingDaoImpl.TrainingRowMapper());
    }

    public Training getOne(Integer id) {
        String sql = "SELECT * FROM trainings WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new TrainingDaoImpl.TrainingRowMapper());
    }

    public void add(Training bean) throws Exception {
        String sql = "INSERT INTO trainings (trainee_id, trainer_id, name," +
                "training_type_id, date, duration) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, bean.getTraineeId(), bean.getTrainerId(), bean.getName(),
                        bean.getTrainingTypeId(), bean.getDate(), bean.getDuration());
    }

    public void update(Training bean) throws Exception {
        String sql = "update trainings set trainee_id=?, trainer_id=?, name=?," +
                "training_type_id=?, date=?, duration=? where id=?";
        jdbcTemplate.update(sql, bean.getTraineeId(), bean.getTrainerId(), bean.getName(),
                bean.getTrainingTypeId(), bean.getDate(), bean.getDuration(),bean.getId());
    }

    public void delete(Integer id) throws Exception {
        String sql = "DELETE FROM trainings WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }

    private static final class TrainingRowMapper implements RowMapper<Training> {

        public Training mapRow(ResultSet rs, int rowNum) throws SQLException {
            Training training = new Training();
            training.setId(rs.getInt("id"));
            training.setTraineeId(rs.getInt("trainee_id"));
            training.setTrainerId(rs.getInt("trainer_id"));
            training.setName(rs.getString("name"));
            training.setTrainingTypeId(rs.getInt("training_type_id"));
            training.setDate(rs.getDate("date"));
            training.setDuration(rs.getInt("duration"));
            return training;
        }
    }
}
