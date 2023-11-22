package Lib.Requete;

import Lib.Interface.IRequete;

public class RequeteLogout implements IRequete {
    private final String Login;

    public RequeteLogout(String login){this.Login = login;}

    public String getLogin(){return Login;}
}
