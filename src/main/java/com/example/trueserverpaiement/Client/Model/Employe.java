package com.example.trueserverpaiement.Client.Model;

public class Employe {
    public String login;

    public String password;

    public boolean isLogged = false;

    public Employe(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Employe() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login=login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password=password;
    }
}
