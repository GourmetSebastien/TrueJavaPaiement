package Client.controller;

import Client.Model.Employe;
import Client.Model.ArticlePanier;
import Client.Model.Facture;
import Lib.Requete.RequeteLogin;
import Lib.Requete.RequeteLogout;
import Lib.Response.ResponseLogin;
import Lib.Response.ResponseLogout;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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


    private  Employe employe;
    private  ObjectOutputStream oos;
    private  ObjectInputStream ois;
    private  Socket csocket;


    @FXML
    public void initialize(Employe emp,Socket csocket){
        this.employe = emp;
        this.csocket = csocket;

        try{
            oos = new ObjectOutputStream(csocket.getOutputStream());
            ois = new ObjectInputStream(csocket.getInputStream());

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    void on_LoginClicked(){
        System.out.println("[Controller] click on login button");

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
                System.out.println("[Controller] Envoi de la requete <<Login>>");
                RequeteLogin req = new RequeteLogin(employe.getLogin(), employe.password);
                oos.writeObject(req);
                ResponseLogin response = (ResponseLogin) ois.readObject();
                System.out.println("[Controller] Reponse <<Login>> reçue");

                if(response.isValide()){
                    System.out.println("[Controller] Connexion réussie");
                    EnableAll();
                    employe.setLogin(LoginField.getText());
                    employe.setPassword(MDPField.getText());
                    employe.isLogged = true;


                }else{
                    System.out.println("[Controller] Connexion échouée");
                }

            }catch (ClassNotFoundException | IOException e){
                throw new RuntimeException();
            }
        }
    }

    @FXML
    public void on_LogoutClicked(){
        System.out.println("[Controller] click on logout button");

        RequeteLogout req = new RequeteLogout(employe.getLogin());

        try {
            System.out.println("[Controller] Envoie de la requete <<Logout>>");
            oos.writeObject(req);

            ResponseLogout response = (ResponseLogout) ois.readObject();
            if(response.isOk()){
                DisableAll();
                employe.isLogged = false;
            }
        }catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void on_ConsultClicked(){

    }

    private void EnableAll(){
        IDField.setDisable(false);
        LogoutButton.setDisable(false);
        LoginButton.setDisable(true);
        ConsultButton.setDisable(false);
        PayerButton.setDisable(false);
        FactureTable.setDisable(false);
        ProductTable.setDisable(false);
    }
    private void DisableAll(){
        IDField.setDisable(true);
        LogoutButton.setDisable(true);
        LoginButton.setDisable(false);
        ConsultButton.setDisable(true);
        PayerButton.setDisable(true);
        FactureTable.setDisable(true);
        ProductTable.setDisable(true);
    }

}