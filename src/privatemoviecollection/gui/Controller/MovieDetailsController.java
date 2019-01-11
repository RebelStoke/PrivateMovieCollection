/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import privatemoviecollection.be.Category;

/**
 * FXML Controller class
 *
 * @author Revy
 */
public class MovieDetailsController implements Initializable {
MainWindowController mwController;
    @FXML
    private ImageView imageView;
    @FXML
    private Label title;
    @FXML
    private Label category;
    @FXML
    private Label rating;
    @FXML
    private ListView<?> listView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {} 
    void setController(MainWindowController controller){
        this.mwController = controller;
        
}
    public void passInfo(String title, ObservableList category, String rating){
        this.title.setText(title);
        listView.getItems().addAll(category);
        this.rating.setText(rating);
        
    
    }

    @FXML
    private void playButton(ActionEvent event) {
        mwController.play();
    }
    
}
