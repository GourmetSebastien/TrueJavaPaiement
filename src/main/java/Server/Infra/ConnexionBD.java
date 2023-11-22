package Server.Infra;

import java.sql.*;
import java.util.Hashtable;

public class ConnexionBD {
    private Connection Connexion;
    public static final String MYSQL = "MySql";
    private String type;
    private static final Hashtable<String,String> drivers;

    static {
        drivers = new Hashtable<String,String>();
        drivers.put(MYSQL,"com.mysql.cj.jdbc.Driver");
    }

    //Constructeur
    public ConnexionBD(){}

    //Méthode d'instances
    public void setDrivers(String type)
    {
        try{
            Class Driver = Class.forName(drivers.get(type));
            this.type = type;
        }
        catch (ClassNotFoundException e){
            System.out.println("[ConnexionBD] Erreur lors du chargement du driver");
        }
    }

    public void ConnexionBD(String ipServer,String BDName, String user,String password){
        String url = null;
        switch (type){
            case MYSQL:
                url = "jdbc:mysql://" + ipServer + "/" + BDName;
                break;
        }

        //Connexion à la BD
        try {
            Connexion = DriverManager.getConnection(url,user,password);
        }
        catch (SQLException e){
            System.out.println("[ConnexionBD] Erreur connexion à la BD :"+e.getMessage());
        }
    }

    //Requete Sélection
    public synchronized ResultSet executeQuery(String sql) throws SQLException{
        Statement stmt = Connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return stmt.executeQuery(sql);
    }

    //Requete MAJ (INSERT, UPDATE, DELETE)
    public synchronized int executeUpdate(String sql) throws SQLException {
        Statement stmt = Connexion.createStatement();
        return stmt.executeUpdate(sql);
    }


    // fermeture de la connexion
    public void close()  {
        try {
            Connexion.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
