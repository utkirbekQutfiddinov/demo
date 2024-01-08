package uz.utkirbek.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.UserDao;
import uz.utkirbek.model.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public User getOne(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserRowMapper());
    }

    public void add(User newBean) throws Exception {
        String sql = "INSERT INTO users (firstname, lastname, username, password, isactive) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, newBean.getFirstname(), newBean.getLastname(),
                                newBean.getUsername(), newBean.getPassword(), newBean.getActive());
    }

    public void update(User bean) throws Exception {
        String sql = "update users set firstname=?, lastname=?, username=?, password=?, isactive=? where id=?";
        jdbcTemplate.update(sql, bean.getFirstname(), bean.getLastname(), bean.getUsername(),
                bean.getPassword(), bean.getActive(),bean.getId());
    }

    public void delete(Integer id) throws Exception {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static final class UserRowMapper implements RowMapper<User>{

        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setFirstname(rs.getString("firstname"));
            user.setLastname(rs.getString("lastname"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setActive(rs.getBoolean("isActive"));
            return user;
        }
    }
}
