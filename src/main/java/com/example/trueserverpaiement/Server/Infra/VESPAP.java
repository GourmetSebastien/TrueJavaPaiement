package com.example.trueserverpaiement.Server.Infra;

import com.example.trueserverpaiement.Client.Model.ArticlePanier;
import com.example.trueserverpaiement.Client.Model.Facture;
import com.example.trueserverpaiement.Client.Model.VerifCard;
import com.example.trueserverpaiement.Lib.Interface.IRequete;
import com.example.trueserverpaiement.Lib.Interface.IResponse;
import com.example.trueserverpaiement.Lib.Requete.*;
import com.example.trueserverpaiement.Lib.Response.*;
import com.example.trueserverpaiement.Server.Exception.FinConnexionException;

import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VESPAP {
    public static synchronized IResponse TraitementRequete(IRequete requete, Socket clientSocket, ConnexionBD connexion) throws FinConnexionException {
        if (requete instanceof RequeteLogin) {
            return TraiteRequeteLOGIN((RequeteLogin) requete, clientSocket, connexion);
        } else if (requete instanceof RequeteLogout) {
            System.out.println("[TraitementRequete] Reception RequeteLogout");
            return TraiteRequeteLOGOUT((RequeteLogout) requete, clientSocket, connexion);
        } else if (requete instanceof RequeteGetFacture) {
            return TraiteGetFacture((RequeteGetFacture) requete, connexion);
        } else if (requete instanceof RequetePayFacture) {
            return TraitePayFacture((RequetePayFacture) requete, connexion);
        } else if (requete instanceof RequeteGetArticles) {
            return TraiteGetArticles((RequeteGetArticles) requete, connexion);
        } else {
            return null;
        }
    }

    private static synchronized ResponseLogin TraiteRequeteLOGIN(RequeteLogin requete, Socket clientSocket, ConnexionBD connexion) {
        System.out.println("[VESPAP] Requete Login reçue");

        String login = requete.getLogin();
        String password = requete.getPassword();
        ResultSet res = null;
        try {
            res = connexion.executeQuery("SELECT * FROM employes WHERE login_emp = '" + login + "' AND password_emp = '" + password + "'");

            if(!res.next()){
                System.out.println("[VESPAP] Login ou MDP incorrect");
                return new ResponseLogin(false,"Login ou mot de passe incorrect");
            }
            else{
                System.out.println("[VESPAP] Login et MDP correct");
                return new ResponseLogin(true,"Login et mot de passe correct");
            }
        }
        catch (SQLException e){
            return new ResponseLogin(false,"Employe Inexistant");
        }
    }

    private static synchronized ResponseLogout TraiteRequeteLOGOUT(RequeteLogout requete, Socket clientSocket, ConnexionBD connexion) throws FinConnexionException {
        System.out.println("[VESPAP] Requete Logout reçue");
        System.out.println("[VESPAP] Fermeture connexion de l'employé : " + requete.getLogin());
        return new ResponseLogout(true);
    }

    private static synchronized ResponseGetFacture TraiteGetFacture(RequeteGetFacture requete, ConnexionBD connexion) {
        System.out.println("[VESPAP] Requete GetFacture reçue");

        int numClient = requete.getNumClient();
        try {
            ResultSet resultSet = connexion.executeQuery("SELECT * FROM factures WHERE idClient = '" + numClient + "' AND paye = -1");
            System.out.println("[VESAP] Requete envoyé à la base de donneés");
            int nbRows = 0;
            List<Facture> factures =new ArrayList<>();
            while (resultSet.next()){
                Facture facture = new Facture();
                facture.setId(resultSet.getInt("idFacutre"));
                facture.setDate(resultSet.getString("jour"));
                facture.setPrixTotal(resultSet.getFloat("montant"));
                facture.setPayer(resultSet.getInt("paye"));

                factures.add(facture);
                nbRows++;
            }

            if(nbRows < 1){
                return new ResponseGetFacture(factures,true);
            }
            else {
                return new ResponseGetFacture(new ArrayList<>(), false);
            }

        } catch (SQLException e) {
            e.printStackTrace(); //Affiche l'erreur
            return new ResponseGetFacture(null,false);
        }
    }

    private static IResponse TraitePayFacture(RequetePayFacture requete, ConnexionBD connexion) {
        System.out.println("[VESPAP] Requete PayFacture reçue");
        if(VerifCard.estValide(requete.getNumCarte()))
        {
            try {
                int rowUpdated = connexion.executeUpdate("UPDATE factures SET paye = 1 WHERE idFacture = '" + requete.getIdFacture() + "'");
                if(rowUpdated >0){
                    System.out.println("[VESPAP] MAj réussie");
                }else{
                    System.out.println("[VESPAP] Erreur MAJ");
                }
                return new ResponsePayFacture(true);
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return new ResponsePayFacture(false);
    }

    private static synchronized ResponseGetArticles TraiteGetArticles(RequeteGetArticles requete, ConnexionBD connexion) {
        try{
            ResultSet resultSet = connexion.executeQuery("SELECT idArticle, intitule,prix, ventes.quantite \n" +
                    "From articles\n" +
                    "INNER JOIN artciles ON ventes.idArticle = articles.idArticle \n" +
                    "WHERE ventes.idFacture = " +requete.getNumFacture() + ";");

            List<ArticlePanier> articles = new ArrayList<>();
            while (resultSet.next()){
                ArticlePanier article= new ArticlePanier();
                article.setId(resultSet.getInt("idArticle"));
                article.setIntitule(resultSet.getString("intitule"));
                article.setPrixUnitaire(resultSet.getFloat("prix"));
                article.setQuantite(resultSet.getInt("ventes.quantite"));

                articles.add(article);
            }
            return new ResponseGetArticles(articles,true);
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("[VESPAP] Erreur BD, Article Inexistant");
            return new ResponseGetArticles(null, false);
        }
    }
}
