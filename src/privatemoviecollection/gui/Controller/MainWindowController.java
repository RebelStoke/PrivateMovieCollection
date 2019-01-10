/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.Controller;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import privatemoviecollection.gui.Model.PMCModel;

/**
 * FXML Controller class
 *
 * @author Revy
 */
public class MainWindowController implements Initializable {

    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button edit;
    private PMCModel model;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remember to Delete outdated AND badly rated movies From the Database for an up-to-date Database! \n Do you want to Delete the movies now?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES)
            {
                
            } else if (alert.getResult() == ButtonType.NO)
            {
                
            }
            model = PMCModel.getInstance();
//        try
//        {
//            String name = "";
//            Desktop.getDesktop().open(new File(name));
//        } catch (IOException ex)
//        {
//            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }    
    
      public void openSongWindow() // opens up SongWindow and sets the connection
    {
        try
        {
            Parent root1;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/View/addMovie.fxml"));
            root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ex)
        {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addMovieMethod(ActionEvent event) {
        openSongWindow();
    }
    
}
