package com.example.trueserverpaiement.Server.Server;

import com.example.trueserverpaiement.Lib.Interface.IRequete;
import com.example.trueserverpaiement.Lib.Interface.IResponse;
import com.example.trueserverpaiement.Lib.Requete.RequeteLogin;
import com.example.trueserverpaiement.Server.Exception.FinConnexionException;
import com.example.trueserverpaiement.Server.Infra.ConnexionBD;
import com.example.trueserverpaiement.Server.Infra.FileAttente;
import com.example.trueserverpaiement.Server.Infra.VESPAP;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

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


                /************************************************/
                // Seb = 192.168.79.128 -- Val = 192.168.15.134 //
                /************************************************/
                ConnexionBD connexionBD = new ConnexionBD(ConnexionBD.MYSQL,"192.168.15.134","PourStudent","Student","PassStudent1_");
                System.out.println("[THCLPO] Connexion acceptée ! ");

                ObjectOutputStream oos=new ObjectOutputStream(clientSocket.getOutputStream());
                System.out.println("[THCLPO] OutputStream() open");

                ObjectInputStream ois = null;
                try{
                    ois= new ObjectInputStream(clientSocket.getInputStream());
                    System.out.println("[THCLPO] Montage des flux réussi");
                } catch (IOException e){
                    System.out.println(e.getMessage());
                    System.out.println("[THCLPO] Montage des flux failed");
                }

                while (onContinue){
                    System.out.println("[THCLPO] début boucle");
                    try {
                        IRequete requete = (IRequete) ois.readObject();
                        System.out.println("[THCLPO] requete : " + requete.toString());
                        IResponse response = VESPAP.TraitementRequete(requete,clientSocket,connexionBD);
                        oos.writeObject(response);
                        oos.flush();
                        // Reste du code
                    } catch (ClassNotFoundException | EOFException e) {
                        e.printStackTrace();
                    }


                }
                clientSocket.close();
                connexionBD.close();
                System.out.println("[THCLPO] Fermeture de la connexion réussie");

            }catch (FinConnexionException e){
                onContinue = false;
            }catch (InterruptedException | IOException | ClassNotFoundException e){
                onContinue = false;
                interrupt = true;
                System.out.println("[THCLPO] Erreur. Arret du Thread! ----- " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("[THCLPO] Erreur. Arret du Thread!" + e.getMessage());
            }
        }
    }
}
