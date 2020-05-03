package com.company;

/**
 * Created By kejba on 29.02.2020
 * to change this template go to Settings -> Editor -> File and Code Templates -> Class
 **/

public class Main {

    public static void main(String[] args) {
        User admin = new User("admin","admin");

        UserList.usersList.add(admin);
        UserList.usersLogins.add(admin.getLogin());
        admin.login();

    }
}
