package com.example.trueserverpaiement.Server;

import com.example.trueserverpaiement.Server.Server.ThreadServer;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args){
        try{
            new ThreadServer(50000,5).start();
        }catch (IOException e){
            System.out.println("Erreur lors de la création du serveur" + e.getMessage());
        }
    }
}
