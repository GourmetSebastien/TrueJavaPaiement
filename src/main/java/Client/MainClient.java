package Client;

import Client.Model.Employe;
import com.example.trueserverpaiement.HelloApplication;

import java.io.IOException;
import java.net.Socket;

public class MainClient {
    public static void main(String[] args)
    {
        System.out.println("[MainClient]Lancement de l'application");
        Socket csocket;

        //Création des fenêtres
        HelloApplication windowClient=new HelloApplication();

        //Création d'un employe
        Employe emp = new Employe();

        //Creation de la socket et connexion au serveur
        try {
            csocket = new Socket("localhost",50000);
        }catch (IOException e){
            throw new RuntimeException();
        }
    }
}
