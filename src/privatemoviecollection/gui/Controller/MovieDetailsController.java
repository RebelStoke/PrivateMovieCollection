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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import privatemoviecollection.be.Category;
import privatemoviecollection.gui.Model.PMCModel;

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
    private ListView<Category> listView;
    
    private PMCModel model;
    private Parent root1;
    @FXML
    private Label personalRating;
    @FXML
    private Label lastView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    //        model = PMCModel.getInstance();
//        Movie m = model.getSelectedMovie();
//        title.setText(m.getName());
//        listView.getItems().addAll(m.getCategories());
//        rating.setText(String.valueOf(m.getRating()));
    } 
    void setController(MainWindowController controller){
        this.mwController = controller;
        
}
    public void passInfo(String title, ObservableList category, String rating, String personalratig, String lastview){
        this.title.setText(title);
        listView.getItems().addAll(category);
        this.rating.setText(rating);
        this.personalRating.setText(personalratig);
        this.lastView.setText(lastview);
    
    }

    @FXML
    private void playButton(ActionEvent event) {
        mwController.play();
//          String path = "/privatemoviecollection/gui/View/mediaPlayer.fxml";
//          model.openWindow(path, root1);
    }
    
}
