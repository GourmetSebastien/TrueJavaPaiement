package Server.Exception;

public class FinConnexionException extends Exception{
    private final String response;
    public FinConnexionException(String response){
        super("Fin de connexion décidé par le protocole");
        this.response = response;
    }

    public String getResponse(){return response;}
}
