package Server.Server;

import Server.Infra.FileAttente;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import Server.Server.ThredClientPool;

public class ThreadServer extends Thread{
    protected int port;
    private final FileAttente fileAttente;
    private final ThreadGroup pool;
    private final int taillePool;
    protected ServerSocket ssocket;

    public ThreadServer(int port,int taillePool) throws IOException {
        this.port = port;
        this.pool = new ThreadGroup("POOL");
        this.taillePool = taillePool;
        this.ssocket = new ServerSocket(port);
        this.fileAttente = new FileAttente();
    }

    @Override
    public void run()
    {
        System.out.println("[THSE] Démarrage du TH Serveur (Pool)...");

        //Création du pool de threads
        for(int i=0; i<taillePool; i++){new ThredClientPool(fileAttente,pool).start();}

        //Attente de connexion
        while (!this.isInterrupted()){
            Socket csocket;
            try {
                ssocket.setSoTimeout(2000);
                csocket = ssocket.accept();
                System.out.println("[THSE] Connexion acceptée, mise en file d'attente.");
                fileAttente.addConnexion(csocket);

            }catch (SocketTimeoutException e){
                //Vérif si thread a été interrompu
            }catch (IOException e){
                System.out.println("[THSE] Erreur I/O");
            }
        }
        System.out.println("[THSE] Serveur interrompu.");
        pool.interrupt();
    }
}
