/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxsimulator;

import fxsimulator.controllers.CanvasController;
import fxsimulator.controllers.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author sowme
 */
public class ProjetROMain extends Application {

    public static Stage primaryStage;
    
    public FXMLLoader loader ;
    
    //public static CanvasController cref;
    
    public static HomeController hc;
    
    
    @Override
    public void start(Stage stage) throws Exception {

        
        primaryStage = stage;
        
        try{
            
            /*
            loader = new FXMLLoader(getClass().getResource("views/Canvas.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            cref = loader.getController();
        
             // Icone de l'application
            Image img = new Image("/fxsimulator/images/icone.png");
            stage.getIcons().add(img);
        
            primaryStage.initStyle(StageStyle.DECORATED);
            primaryStage.setScene(newScene);
            primaryStage.show(); 
            */
            
            loader = new FXMLLoader(getClass().getResource("views/home.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            hc = loader.getController();
        
             // Icone de l'application
            Image img = new Image("/fxsimulator/images/icone.png");
            stage.getIcons().add(img);
        
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(newScene);
            primaryStage.show(); 
  
            
        }catch(NullPointerException e){
            System.out.println(e);
        }
        
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
