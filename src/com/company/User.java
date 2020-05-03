package com.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Created By kejba on 29.02.2020
 * to change this template go to Settings -> Editor -> File and Code Templates -> Class
 **/

public class User {
    private String login;
    private String password;
    private String registeredDate;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        registeredDate = LocalDateTime.now().format(format);
    }

    public void login() {
        checkLogin();
        checkPassword();
        System.out.println("Zalogowano poprawnie");
        showMenu();
    }

    public String getLogin() {
        return login;
    }

    private void checkLogin() {
        String lg;
        short wrongLoginCount = 0;

        do {
            System.out.println("Podaj login: ");
            Scanner sc = new Scanner(System.in);
            lg = sc.nextLine().trim();

            if (lg.equals(login)) {
                break;
            } else {
                System.out.println("Niepoprawny login");
                System.out.println("Liczba niepoprawnych loginow: " + ++wrongLoginCount);
            }
        } while (wrongLoginCount < 3);
        if (wrongLoginCount == 3) {
            System.out.println("3 nieudane wprowadzenie. Konto zablokowane");
            System.exit(1);
        }
    }

    private void checkPassword() {
        String passwd;
        short wrongPasswordCount = 0;

        do {
            System.out.println("Podaj haslo: ");
            Scanner sc = new Scanner(System.in);
            passwd = sc.nextLine().trim();

            if (passwd.equals(password)) {
                break;
            } else {
                System.out.println("Niepoprawne hasło");
                System.out.println("Liczba niepoprawnych hasel: " + ++wrongPasswordCount);
            }
        } while (wrongPasswordCount < 3);
        if (wrongPasswordCount == 3) {
            System.out.println("3 nieudane wprowadzenie. Konto zablokowane");
            System.exit(1);
        }
    }

    private void showCurrentDate() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE, dd-MM-yyyy HH:mm:ss");
        String formattedDate = LocalDateTime.now().format(format);

        System.out.println("Aktualna data \n" + formattedDate);
    }

    private void finishProgram() {
        while (true) {
            System.out.println("Czy na pewno chcesz wyjsc? (Y/N)");
            Scanner sc = new Scanner(System.in);
            char decision = sc.next().charAt(0);
            decision = Character.toUpperCase(decision);
            switch (decision) {
                case 'Y': {
                    System.out.println("Do widzenia");
                    System.exit(0);
                }
                case 'N': {
                    System.out.println("Anulowano");
                    showMenu();
                    break;
                }
                default: {
                    System.out.println("Niepoprawny wybor, sprobuj ponownie");
                    break;
                }
            }
        }
    }

    private void changePassword() {
        System.out.println("Nacisnij 0 aby anulowac");

        boolean cancel = false;
        do {
            System.out.println("Wprowadz aktualne haslo");
            Scanner sc = new Scanner(System.in);
            String currentPasswd = sc.nextLine().trim();
            if (currentPasswd.equals("0")) {
                cancel = true;
                System.out.println("Anulowano");
            } else if (!currentPasswd.equals(password)) {
                System.out.println("Niepoprwane haslo");
                changePassword();
            } else {
                System.out.println("Wprowadz nowe haslo");
                String newPasswd = sc.nextLine().trim();
                System.out.println("Potwierdz nowe haslo");
                String confirm = sc.nextLine().trim();

                if (newPasswd.equals(confirm)) {
                    password = newPasswd;
                    cancel = true;
                    System.out.println("Haslo poprwanie zmienione");
                } else {
                    System.out.println("Podane hasla roznia sie");
                }
            }
        } while (!cancel);
    }

    private void showPassword() {
        System.out.println("Twoje haslo: " + password);
    }

    private void addUser() {
        String login, password;

        System.out.println("Podaj login: ");
        Scanner sc = new Scanner(System.in);
        login = sc.nextLine().trim();

        boolean contains = UserList.usersLogins.contains(login);
        if (!contains) {
            System.out.println("Podany login jest wolny");
            System.out.println("Podaj hasło: ");
            password = sc.nextLine().trim();

            User user = new User(login, password);
            UserList.usersList.add(user);
            UserList.usersLogins.add(login);
        } else {
            System.out.println("Podany login jest juz zajety. Sprobuj ponownie");
            addUser();
        }
    }

    private void showUsersList() {
        if (UserList.usersList.isEmpty()) {
            System.out.println("Lista jest pusta");
        } else {
            System.out.println("Lista uzytkownikow");
            for (int i = 0; i < UserList.usersList.size(); i++) {
                System.out.printf("User %d, login: %s, zarejestrowany: %s%n", i + 1, UserList.usersList.get(i).login, UserList.usersList.get(i).registeredDate);
            }
        }
    }

    private void showMenu() {
        while (true) {
            showCurrentDate();
            System.out.println("Wybierz akcje:");
            System.out.println("1. Dodaj uzytkownika");
            System.out.println("2. Wyswietl liste uzytkownikow");
            System.out.println("7. Pokaz aktualne haslo");
            System.out.println("8. Zmien haslo");
            System.out.println("9. Wyloguj");
            System.out.println("0. Zakoncz program");

            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();

            switch (choice) {
                case 0:
                    finishProgram();
                case 1:
                    addUser();
                    break;
                case 2:
                    showUsersList();
                    break;
                case 7:
                    showPassword();
                    break;
                case 8:
                    changePassword();
                    break;
                case 9:
                    System.out.println("Wylogowano poprawnie");
                    login();
                    break;
                default:
                    System.out.println("Niepoprawny wybor");
            }
        }
    }
}
