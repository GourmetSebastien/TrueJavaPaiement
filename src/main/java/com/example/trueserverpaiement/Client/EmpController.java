package com.example.trueserverpaiement.Client;

import com.example.trueserverpaiement.Client.Model.Employe;
import com.example.trueserverpaiement.Client.Model.ArticlePanier;
import com.example.trueserverpaiement.Client.Model.Facture;
import com.example.trueserverpaiement.Lib.Requete.RequeteGetFacture;
import com.example.trueserverpaiement.Lib.Requete.RequeteLogin;
import com.example.trueserverpaiement.Lib.Requete.RequeteLogout;
import com.example.trueserverpaiement.Lib.Response.ResponseGetFacture;
import com.example.trueserverpaiement.Lib.Response.ResponseLogin;
import com.example.trueserverpaiement.Lib.Response.ResponseLogout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class EmpController {
    @FXML
    private TextField LoginField;
    @FXML
    private TextField MDPField;
    @FXML
    private TextField IDField;
    @FXML
    private TextField CBFieeld;
    @FXML
    private Button LoginButton;
    @FXML
    private Button LogoutButton;
    @FXML
    private Button PayerButton;
    @FXML
    private Button ConsultButton;
    @FXML
    private TableView<Facture> FactureTable;
    @FXML
    private TableColumn<Facture,Integer> IdFactureColumn;
    @FXML
    private TableColumn<Facture,String> DateColumn;
    @FXML
    private TableColumn<Facture,Float> MontantColumn;
    @FXML
    private TableView<ArticlePanier> ProductTable;
    @FXML
    private TableColumn<ArticlePanier,String> IntituleColumn;
    @FXML
    private TableColumn<ArticlePanier,Integer> QuantiteColumn;
    @FXML
    private TableColumn<ArticlePanier,Float> PrixUniColumn;


    private ObservableList<Facture> ListeFacture;
    private ObservableList<ArticlePanier> ListeArtcile;
    private  Employe employe;
    private  ObjectOutputStream oos;
    private  ObjectInputStream ois;
    private  Socket csocket;
    private boolean socketCreated = false;


    @FXML
    public void initialize(){
        DisableAll();
        ListeFacture = FXCollections.observableArrayList();
        ListeArtcile = FXCollections.observableArrayList();

        if(!socketCreated){
            try{
                System.out.println("[INITIALIZE] Tentative de Connexion au Serveur");
                this.csocket = new Socket("localhost",50000);
                this.employe = new Employe();
                System.out.println("[INITIALIZE] Socket connecté au Serveur");

                IdFactureColumn.setCellValueFactory(new PropertyValueFactory("Id"));
                DateColumn.setCellValueFactory(new PropertyValueFactory("date"));
                MontantColumn.setCellValueFactory(new PropertyValueFactory("PrixTotal"));

                IntituleColumn.setCellValueFactory(new PropertyValueFactory("intitule"));
                QuantiteColumn.setCellValueFactory(new PropertyValueFactory("quantite"));
                PrixUniColumn.setCellValueFactory(new PropertyValueFactory("prixUnitaire"));

                FactureTable.setItems(ListeFacture);
                ProductTable.setItems(ListeArtcile);

                System.out.println("[INITIALIZE] setItem()");


                oos = new ObjectOutputStream(csocket.getOutputStream());
                oos.flush();
                ois = new ObjectInputStream(csocket.getInputStream());
                System.out.println("[INITIALIZE] ObjectSteam succes");
                System.out.println("[INITIALIZE] Fin initialize");

            }catch (IOException e){
                System.out.println("[INITIALIZE] Connexion Error : " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void shutdown() {
        System.out.println("[Controller] SHUTDOWN Fenêtre fermée");

        try {
            if (socketCreated){
                if(employe.isLogged){
                    on_LogoutClicked();
                }
            }

            csocket.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        System.exit(0);
    }

    @FXML
    void on_LoginClicked() throws IOException {
        System.out.println("[LOGIN_CLICKED] click on login button");

        if (LoginField.getText().isEmpty()) {
            // Les champs de login ou de mot de passe sont vides
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText("Champs vides");
            if(MDPField.getText().isEmpty()){
                alert.setContentText("Veuillez remplir les champs de login et de mot de passe.");
            }else {
                alert.setContentText("Veuillez remplir le champs de login");
            }
            alert.showAndWait();
        } else {
            if(MDPField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setHeaderText("Champs vides");
                alert.setContentText("Veuillez remplir le champs de mot de passe.");
                alert.showAndWait();
            }

            try {
                System.out.println("[LOGIN_CLICKED] Envoi de la requete <<Login>>");
                System.out.println("[LOGIN_CLICKED] Login : " + LoginField.getText() + " --- password : " + MDPField.getText());
                RequeteLogin req = new RequeteLogin(LoginField.getText(), MDPField.getText());
                oos.writeObject(req);
                System.out.println("[LOGIN_CLICKED] requete <<Login>> envoyée");
                ResponseLogin response = (ResponseLogin) ois.readObject();
                System.out.println("[LOGIN_CLICKED] Reponse <<Login>> reçue");

                if(response.isValide()){
                    System.out.println("[LOGIN_CLICKED] Connexion réussie");
                    employe.setLogin(LoginField.getText());
                    employe.setPassword(MDPField.getText());
                    employe.isLogged = true;
                    EnableAll();
                    LoginButton.setDisable(true);
                }else{
                    System.out.println("[LOGIN_CLICKED] Connexion échouée");
                }

            }catch (ClassNotFoundException | IOException e){
                throw new RuntimeException();
            }
        }
    }

    @FXML
    public void on_LogoutClicked(){
        System.out.println("[on_LogoutClicked] click on logout button");

        RequeteLogout req = new RequeteLogout(employe.getLogin());

        try {
            System.out.println("[on_LogoutClicked] Envoie de la requete <<Logout>>");
            System.out.println("[on_LogoutClicked] Login : " + req.getLogin());
            oos.writeObject(req);
            System.out.println("[on_LogoutClicked] Requete envoyée");

            ResponseLogout response = (ResponseLogout) ois.readObject();
            if(response.isOk()){
                DisableAll();
                LoginButton.setDisable(false);
                employe.isLogged = false;
            }
            if(oos != null){
                oos.close();
            }
            if(ois != null){
                ois.close();
            }

        }catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void on_ConsultClicked(){
        System.out.println("[Controller] click on Consult button");

        System.out.println("[Controller] Envoie Requete GetArticles");
        RequeteGetFacture req = new RequeteGetFacture(Integer.parseInt(IDField.getText()));
        try {
            oos.writeObject(req);

            ResponseGetFacture response = (ResponseGetFacture) ois.readObject();
            if(response.isFound() == false){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setHeaderText("Pas de factures trouvée dans la base de données");
                alert.setContentText("Vérifier les données du client");
                alert.showAndWait();
            }

        }catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    private void EnableAll(){
        IDField.setDisable(false);
        LogoutButton.setDisable(false);
        LoginButton.setDisable(false);
        ConsultButton.setDisable(false);
        PayerButton.setDisable(false);
        FactureTable.setDisable(false);
        ProductTable.setDisable(false);
    }

    private void DisableAll(){
        IDField.setDisable(true);
        LogoutButton.setDisable(true);
        ConsultButton.setDisable(true);
        PayerButton.setDisable(true);
        FactureTable.setDisable(true);
        ProductTable.setDisable(true);
    }
}