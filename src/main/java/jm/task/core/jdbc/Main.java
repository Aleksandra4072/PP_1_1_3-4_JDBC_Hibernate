package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Inna", "Kim", (byte) 20);
        userService.saveUser("Yuliya", "Umarova", (byte) 25);
        userService.saveUser("Violetta", "Lee", (byte) 31);
        userService.saveUser("Vitaliy", "Popov", (byte) 38);

        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
