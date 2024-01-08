package uz.utkirbek.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.TraineeDao;
import uz.utkirbek.model.Trainee;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TraineeDaoImpl implements TraineeDao {
    private JdbcTemplate jdbcTemplate;

    public TraineeDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Trainee> getAll() {
        String sql = "SELECT * FROM trainees";
        return jdbcTemplate.query(sql, new TraineeRowMapper());
    }

    public Trainee getOne(Integer id) {
        String sql = "SELECT * FROM trainees WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new TraineeRowMapper());
    }

    public void add(Trainee bean) throws Exception {
        String sql = "INSERT INTO trainees (userId, birthdate, address) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, bean.getUserId(), bean.getUserId(), bean.getBirthdate(), bean.getAddress());
    }

    public void update(Trainee bean) throws Exception {
        String sql = "update trainees set userId=?, address=?, birthdate=? where id=?";
        jdbcTemplate.update(sql, bean.getUserId(),bean.getAddress(), bean.getBirthdate(),bean.getId());
    }

    public void delete(Integer id) throws Exception {
        String sql = "DELETE FROM trainees WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static final class TraineeRowMapper implements RowMapper<Trainee> {

        public Trainee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Trainee trainee = new Trainee();
            trainee.setId(rs.getInt("id"));
            trainee.setUserId(rs.getInt("userid"));
            trainee.setBirthdate(rs.getDate("birthdate"));
            trainee.setAddress(rs.getString("address"));
            return trainee;
        }
    }
}
