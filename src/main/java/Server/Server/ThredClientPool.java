package Server.Server;

import Lib.Interface.IRequete;
import Lib.Interface.IResponse;
import Server.Exception.FinConnexionException;
import Server.Infra.ConnexionBD;
import Server.Infra.FileAttente;
import Server.Infra.VESPAP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThredClientPool extends Thread{
    private final FileAttente fileAttente;
    private boolean interrupt = false;
    private boolean onContinue = true;

    private final int numero;
    private static int nbClient = 0;

    private Socket clientSocket;

    public ThredClientPool(FileAttente connexionEnAttente, ThreadGroup pool) {
        super(pool,"[THCLPO] " + nbClient);
        this.fileAttente = connexionEnAttente;
        this.numero = ++nbClient;
        this.clientSocket = null;
    }

    @Override
    public void run(){
        while (!interrupt){
            System.out.println("[THCLPO] " + numero + " en attente d'une connexion.");
            try {
                clientSocket = fileAttente.getConnexion();
                //Connexion à la BD
                ConnexionBD connexionBD = new ConnexionBD();
                connexionBD.setDrivers(ConnexionBD.MYSQL);
                connexionBD.ConnexionBD("192.168.79.128","PourStudent","Student","PassStudent1_");
                System.out.println("[THCLPO] Connexion acceptée ! ");

                ObjectOutputStream oos=new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ois= new ObjectInputStream(clientSocket.getInputStream());

                while (onContinue){
                    IRequete requete = (IRequete) ois.readObject();
                    IResponse response = VESPAP.TraitementRequete(requete,clientSocket,connexionBD);
                    oos.writeObject(response);
                }
                clientSocket.close();
                connexionBD.close();
                System.out.println("[THCLPO] Fermeture de la connexion réussie");

            }catch (FinConnexionException e){
                onContinue = false;
            }catch (InterruptedException | IOException | ClassNotFoundException e){
                onContinue = false;
                interrupt = true;
                System.out.println("[THCLPO] Erreur. Arret du Thread!" + e.getMessage());
            }
        }
    }
}
