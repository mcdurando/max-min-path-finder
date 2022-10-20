/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxsimulator.controllers;

import com.jfoenix.controls.JFXButton;
//import static fxsimulator.ProjetROMain.cref;
import static fxsimulator.ProjetROMain.primaryStage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Mac Carthy
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXButton btnStart;
    
    public static CanvasController cref;
    
    @FXML
    private TextFlow textFlow;

    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     // CLOSE THE APP
    public void closeHandle(ActionEvent aev) {
        System.exit(0);
    }
    
      // CLOSE THE APP
    public void startAppHandle(ActionEvent aev) {
        
        
            
//         loader = new FXMLLoader(getClass().getResource("views/home.fxml"));
//            Parent root = loader.load();
//            Scene newScene = new Scene(root);
//            cref = loader.getController();
//        
//             // Icone de l'application
//            Image img = new Image("/fxsimulator/images/icone.png");
//            stage.getIcons().add(img);
//        
//            primaryStage.initStyle(StageStyle.DECORATED);
//            primaryStage.setScene(newScene);
//            primaryStage.show(); 
            
            
              // Close the home page
               Stage stage_home = (Stage) textFlow.getScene().getWindow() ;
               stage_home.close() ;
            
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxsimulator/views/Canvas.fxml"));
            
             try{
                loader.load();
                
            }catch(IOException ex){
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                //System.out.println("ERREUR");
            }
             
            cref = loader.getController();
           
            Parent p = loader.getRoot();
            
            Stage stage = new Stage();
            
              // Icone de l'application
            Image img = new Image("/fxsimulator/images/icone.png");
            stage.getIcons().add(img);
            
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(p));
            stage.show();
    }
    
}
