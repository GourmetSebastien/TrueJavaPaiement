package Server.Infra;

import java.net.Socket;
import java.util.LinkedList;

public class FileAttente {
    private final LinkedList<Socket> fileAttente;

    public FileAttente(){fileAttente = new LinkedList<>();}

    public synchronized void addConnexion(Socket client) {
        fileAttente.add(client);
    }

    public synchronized Socket getConnexion() throws InterruptedException{
        while (fileAttente.isEmpty()) wait();
        return fileAttente.remove();
    }
}
