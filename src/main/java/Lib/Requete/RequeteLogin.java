package Lib.Requete;

import Lib.Interface.IRequete;

public class RequeteLogin implements IRequete {
    private final String Login;
    private final String Password;

    public RequeteLogin(String login, String password){
        this.Login = login;
        this.Password = password;
    }
    public String getLogin() {return Login;}
    public String getPassword(){return Password;}
}
