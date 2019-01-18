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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
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
    private Parent root1;
    @FXML
    private Label lastView;
    @FXML
    private ProgressBar ratingBar;
    @FXML
    private ProgressBar personalRBar;
    @FXML
    private Label ratingLabel;
    @FXML
    private Label personalRLabel;
    @FXML
    private ComboBox<Category> catBox;

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
    public void passInfo(String title, ObservableList category, float rating, float personalrating, String lastview){
        this.title.setText(title);
        catBox.setPromptText(category.get(0).toString());
        catBox.setItems(category);
        this.lastView.setText(lastview);
        ratingBar.setProgress(rating*0.1);
        personalRBar.setProgress(personalrating*0.1);
        ratingLabel.setText(rating+"/10");
        personalRLabel.setText(personalrating+"/10");
    }

    @FXML
    private void playButton(ActionEvent event) {
        mwController.play();
//          String path = "/privatemoviecollection/gui/View/mediaPlayer.fxml";
//          model.openWindow(path, root1);
    }
    
}
