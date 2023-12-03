package com.example.trueserverpaiement.Server.Infra;

import com.example.trueserverpaiement.Client.Model.*;
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
        System.out.println("[TRAIT_REQUETE_LOGIN] Requete Login reçue");

        String login = requete.getLogin();
        String password = requete.getPassword();
        ResultSet res = null;
        try {
            res = connexion.executeQuery("SELECT * FROM employes WHERE login_emp = '" + login + "' AND password_emp = '" + password + "'");

            if(!res.next()){
                System.out.println("[TRAIT_REQUETE_LOGIN] Login ou MDP incorrect");
                return new ResponseLogin(false,"Login ou mot de passe incorrect");
            }
            else{
                System.out.println("[TRAIT_REQUETE_LOGIN] Login et MDP correct");
                return new ResponseLogin(true,"Login et mot de passe correct");
            }
        }
        catch (SQLException e){
            return new ResponseLogin(false,"Employe Inexistant");
        }
    }

    private static synchronized ResponseLogout TraiteRequeteLOGOUT(RequeteLogout requete, Socket clientSocket, ConnexionBD connexion) throws FinConnexionException {
        System.out.println("[TRAIT_REQUETE_LOGOUT] Requete Logout de l'employé : " + requete.getLogin() + " reçue");
        return new ResponseLogout(true);
    }

    private static synchronized ResponseGetFacture TraiteGetFacture(RequeteGetFacture requete, ConnexionBD connexion) {
        System.out.println("[TRAIT_GetFACTURE] Requete GetFacture reçue");

        int numClient = requete.getNumClient();
        try {
            ResultSet resultSet = connexion.executeQuery("SELECT * FROM factures WHERE idClient = " + numClient + " AND paye = -1");
            System.out.println("SELECT * FROM factures WHERE idClient = " + numClient + " AND paye = -1");
            System.out.println("[TRAIT_GetFACTURE] Requete envoyé à la base de donneés");
            int nbRows = 0;
            List<FacSerializable> factures =new ArrayList<>();
            while (resultSet.next()){
                System.out.println("[TRAIT_GetFACTURE] Resultat : " + resultSet.getString("jour"));
                FacSerializable facture = new FacSerializable();
                facture.setId(resultSet.getInt("idFacture"));
                facture.setDate(resultSet.getString("jour"));
                facture.setPrixTotal(resultSet.getFloat("montant"));
                facture.setPayer(resultSet.getInt("paye"));

                factures.add(facture);
                nbRows++;
            }

            if(nbRows < 1){
                return new ResponseGetFacture(new ArrayList<>(),false);
            }
            else {
                System.out.println("[TRAIT_GetFACTURE] idFacture = " + factures.get(0).getId());
                return new ResponseGetFacture(factures, true);
            }

        } catch (SQLException e) {
            e.printStackTrace(); //Affiche l'erreur
            return new ResponseGetFacture(new ArrayList<>(),false);
        }
    }

    private static IResponse TraitePayFacture(RequetePayFacture requete, ConnexionBD connexion) {
        System.out.println("[TRAIT_PAIEFACTURE] Requete PayFacture reçue");
        if(VerifCard.estValide(requete.getNumCarte()))
        {
            try {
                int rowUpdated = connexion.executeUpdate("UPDATE factures SET paye = 1 WHERE idFacture = " + requete.getIdFacture() + ";");
                if(rowUpdated >0){
                    System.out.println("[TRAIT_PAIEFACTURE] MAj réussie");
                }else{
                    System.out.println("[TRAIT_PAIEFACTURE] Erreur MAJ");
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
            System.out.println("[TRAIT_GETARTICLES] Numero de Facture : " + requete.getNumFacture());
            ResultSet resultSet = connexion.executeQuery("select idArticle, intitule, prix, quantite from ventes inner join articles using (idArticle) where idFacture = " + requete.getNumFacture() + ";");

            int nbRows = 0;
            List<ArtPanSerializable> articles = new ArrayList<>();
            while (resultSet.next()){
                ArtPanSerializable article= new ArtPanSerializable();
                article.setId(resultSet.getInt("idArticle"));
                article.setIntitule(resultSet.getString("intitule"));
                article.setPrixUnitaire(resultSet.getFloat("prix"));
                article.setQuantite(resultSet.getInt("quantite"));

                articles.add(article);
                nbRows++;
            }

            if(nbRows < 1){
                return new ResponseGetArticles(new ArrayList<>(), false);
            }else{
                return new ResponseGetArticles(articles, true);
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("[VESPAP] Erreur BD, Article Inexistant");
            return new ResponseGetArticles(new ArrayList<>(), false);
        }
    }
}
