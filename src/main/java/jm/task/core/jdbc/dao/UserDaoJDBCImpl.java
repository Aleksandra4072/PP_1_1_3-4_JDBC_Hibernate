package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        final String sqlCommand = "CREATE TABLE IF NOT EXISTS users ("
                + "id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL, "
                + "name VARCHAR(30) NOT NULL, lastName VARCHAR(30) NOT NULL, "
                + "age INT NOT NULL)";
        try (final Connection connect = Util.getConnection();
             Statement statement = connect.createStatement()) {
            statement.executeUpdate(sqlCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        final String sqlCommand = "DROP TABLE IF EXISTS users";
        try (final Connection connect = Util.getConnection();
             Statement statement = connect.createStatement()) {
            statement.executeUpdate(sqlCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String sqlCommand = "INSERT INTO users(name, lastName, age) VALUES (?, ?, ?)";
        try (final Connection connect = Util.getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sqlCommand)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        User user = new User();
        user.setId(id);
        final String sqlCommand = "DELETE FROM users WHERE id = ?";
        try (final Connection connect = Util.getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sqlCommand)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        User user = new User();
        final String sqlCommand = "SELECT * FROM users";
        ArrayList<User> result = new ArrayList<User>();
        try (final Connection connect = Util.getConnection();
             Statement statement = connect.createStatement()) {
            ResultSet rs = statement.executeQuery(sqlCommand);
            while (rs.next()) {
                user.setId(rs.getLong(1));
                user.setName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setAge(rs.getByte(4));
                result.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void cleanUsersTable() {
        final String sql = "DELETE FROM users";
        try (final Connection connect = Util.getConnection();
             Statement statement = connect.createStatement()) {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
