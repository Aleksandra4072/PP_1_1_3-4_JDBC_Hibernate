package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class UserDaoHibernateIMPL implements UserDao{
    public UserDaoHibernateIMPL() {
    }

    @Override
    public void createUsersTable() {
        final String sqlCommand = "CREATE TABLE IF NOT EXISTS users "
                + "(id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL, "
                + "name VARCHAR(30) NOT NULL, lastName VARCHAR(30) NOT NULL, "
                + "age INT NOT NULL)";
        Transaction transaction = null;
        try (Session s = Util.getSF().getCurrentSession()) {
            transaction = s.beginTransaction();
            s.createSQLQuery(sqlCommand).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        final String sqlCommand = "DROP TABLE IF EXISTS users";
        Transaction transaction = null;
        try (Session s = Util.getSF().getCurrentSession()) {
            transaction = s.beginTransaction();
            s.createSQLQuery(sqlCommand).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastName, age);
        try (Session s = Util.getSF().getCurrentSession()) {
            transaction = s.beginTransaction();
            s.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session s = Util.getSF().getCurrentSession()) {
            transaction = s.beginTransaction();
            s.delete(s.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        try (Session s = Util.getSF().getCurrentSession()) {
            transaction = s.beginTransaction();
            return s.createQuery("From User", User.class).list();
        } catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session s = Util.getSF().getCurrentSession()) {
            transaction = s.beginTransaction();
            s.createSQLQuery("DELETE FROM users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
