package com.example.trueserverpaiement.Client;

import com.example.trueserverpaiement.Client.Model.Employe;
import com.example.trueserverpaiement.Client.Model.ArticlePanier;
import com.example.trueserverpaiement.Client.Model.Facture;
import com.example.trueserverpaiement.Lib.Requete.*;
import com.example.trueserverpaiement.Lib.Response.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

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

    @FXML
    private TextField NameTextField;


    private ObservableList<Facture> ListeFacture;
    private ObservableList<ArticlePanier> ListeArtcile;
    private  Employe employe;
    private  ObjectOutputStream oos;
    private  ObjectInputStream ois;
    private  Socket csocket;
    private boolean socketCreated = false;

    Facture temp;
    Date lastClickTime;


    @FXML
    public void initialize(){
        DisableAll();
        ListeFacture = FXCollections.observableArrayList();
        ListeArtcile = FXCollections.observableArrayList();

        IdFactureColumn.setCellValueFactory(new PropertyValueFactory("Id"));
        DateColumn.setCellValueFactory(new PropertyValueFactory("date"));
        MontantColumn.setCellValueFactory(new PropertyValueFactory("PrixTotal"));

        IntituleColumn.setCellValueFactory(new PropertyValueFactory("intitule"));
        QuantiteColumn.setCellValueFactory(new PropertyValueFactory("quantite"));
        PrixUniColumn.setCellValueFactory(new PropertyValueFactory("prixUnitaire"));

        FactureTable.setItems(ListeFacture);
        ProductTable.setItems(ListeArtcile);

        System.out.println("[INITIALIZE] setItem()");

        this.employe = new Employe();
    }

    @FXML
    public void shutdown() {
        System.out.println("[Controller] SHUTDOWN Fenêtre fermée");

        if (employe.isLogged){
            on_LogoutClicked();
        }
        System.exit(0);
    }

    @FXML
    void on_LoginClicked() throws IOException {
        System.out.println("[LOGIN_CLICKED] click on login button");

        /******************************************************************/
        System.out.println("[INITIALIZE] Tentative de Connexion au Serveur");
        this.csocket = new Socket("localhost",50000);
        System.out.println("[INITIALIZE] Socket connecté au Serveur");
        socketCreated = true;

        oos = new ObjectOutputStream(csocket.getOutputStream());
        oos.flush();
        ois = new ObjectInputStream(csocket.getInputStream());
        System.out.println("[INITIALIZE] ObjectSteam succes");
        System.out.println("[INITIALIZE] Fin initialize");
        /******************************************************************/

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
                System.out.println("Erreur : " + e.getMessage());
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
                csocket.close();
                socketCreated = false;
                oos.close();
                ois.close();
                ListeArtcile.clear();
                ListeFacture.clear();
                IDField.setText("");
                LoginField.setText("");
                MDPField.setText("");
            }

        }catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void on_ConsultClicked(){
        System.out.println("[Controller] click on Consult button");

        if(ListeFacture.size() > 0){
            ListeFacture.clear();
            if(ListeArtcile.size() > 0){
                ListeArtcile.clear();
            }
        }

        System.out.println("[Controller] Envoie Requete GetFacture");
        RequeteGetFacture req = new RequeteGetFacture(Integer.parseInt(IDField.getText()));
        try {
            oos.writeObject(req);

            ResponseGetFacture response = (ResponseGetFacture) ois.readObject();
            if(!response.isFound()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setHeaderText("Pas de factures trouvée dans la base de données");
                alert.setContentText("Vérifier les données du client");
                alert.showAndWait();
            } else {
                System.out.println("[CONTROLLER] Copy response in ListeFacture");
                System.out.println("[CONTROLLER] idFacture = " + response.getListFacture().get(0).getId());
                for(int i = 0; i<response.getListFacture().size(); i++)
                {
                    ListeFacture.add(new Facture(response.getListFacture().get(i).getId(), response.getListFacture().get(i).getDate(), response.getListFacture().get(i).getPrixTotal(), response.getListFacture().get(i).getPayer()));
                }

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
        CBFieeld.setDisable(false);
    }

    private void DisableAll(){
        IDField.setDisable(true);
        LogoutButton.setDisable(true);
        ConsultButton.setDisable(true);
        PayerButton.setDisable(true);
        FactureTable.setDisable(true);
        ProductTable.setDisable(true);
        CBFieeld.setDisable(true);
    }

    @FXML
    void doubleClick(MouseEvent event) {
        Facture row = FactureTable.getSelectionModel().getSelectedItem();
        if (row == null) return;
        if(row != temp){
            temp = row;
            lastClickTime = new Date();
        } else if(row == temp) {
            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();
            if (diff < 300){ //another click registered in 300 millis
                System.out.println("[DOUBLE_CLICK] on row");

                if(ListeArtcile.size() > 0){
                    ListeArtcile.clear();
                }

                System.out.println("[DOUBLE_CLICK] Envoie Requete GetArticles");

                RequeteGetArticles req = new RequeteGetArticles(ListeFacture.get(FactureTable.getSelectionModel().getSelectedIndex()).getId());
                try {
                    oos.writeObject(req);

                    ResponseGetArticles response = (ResponseGetArticles) ois.readObject();
                    if(!response.isFound()){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Attention");
                        alert.setHeaderText("Pas d'article trouvée dans la base de données");
                        alert.setContentText("Vérifier les données de la facture");
                        alert.showAndWait();
                    } else {
                        System.out.println("[DOUBLE_CLICK] Copy response in ListeFacture");
                        System.out.println("[DOUBLE_CLICK] idFacture = " + response.getListArticles().get(0).getId());
                        for(int i = 0; i<response.getListArticles().size(); i++)
                        {
                            ListeArtcile.add(new ArticlePanier(response.getListArticles().get(i).getId(), response.getListArticles().get(i).getIntitule(), response.getListArticles().get(i).getPrixUnitaire(), response.getListArticles().get(i).getQuantite()));
                        }

                    }

                }catch (IOException | ClassNotFoundException e){
                    throw new RuntimeException(e);
                }
            } else {
                lastClickTime = new Date();
            }
        }
    }

    @FXML
    void PayerFacture(){
        System.out.println("[PAYER_FACTURE] Click on Button");

        RequetePayFacture requete = new RequetePayFacture(ListeFacture.get(FactureTable.getSelectionModel().getSelectedIndex()).getId(), CBFieeld.getText(), NameTextField.getText());

        try {
            oos.writeObject(requete);

            ResponsePayFacture response = (ResponsePayFacture) ois.readObject();

            if(response.isValide()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Effectué");
                alert.setHeaderText("La facture a bien été réglée");
                alert.setContentText("Bonne journée");
                alert.showAndWait();
                ListeFacture.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setHeaderText("La carte n'est pas valide");
                alert.setContentText("Veuillez vérifier les données entrée");
                alert.showAndWait();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[PAYER_FACTURE] Erreur : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}