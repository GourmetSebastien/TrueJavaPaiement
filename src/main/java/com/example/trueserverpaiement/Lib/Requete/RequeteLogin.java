package com.example.trueserverpaiement.Lib.Requete;

import com.example.trueserverpaiement.Lib.Interface.IRequete;

public class RequeteLogin implements IRequete {
    private String Login;
    private String Password;

    public RequeteLogin(String login, String password){
        this.Login = login;
        this.Password = password;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getLogin() {return Login;}
    public String getPassword(){return Password;}

    @Override
    public String toString() {
        return "RequeteLogin{" +
                "Login='" + Login + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
