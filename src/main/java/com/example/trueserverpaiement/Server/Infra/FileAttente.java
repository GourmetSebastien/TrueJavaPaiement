package com.example.trueserverpaiement.Server.Infra;

import java.net.Socket;
import java.util.LinkedList;

public class FileAttente {
    private final LinkedList<Socket> fileAttente;

    public FileAttente(){fileAttente = new LinkedList<>();}

    public synchronized void addConnexion(Socket client) {
        System.out.println("[FILEATTENTE] addConnexion()");
        fileAttente.addLast(client);
        notify();
    }

    public synchronized Socket getConnexion() throws InterruptedException{
        System.out.println("[FILEATTENTE] getConnexion()");
        while (fileAttente.isEmpty()) wait();
        return fileAttente.remove();
    }
}
