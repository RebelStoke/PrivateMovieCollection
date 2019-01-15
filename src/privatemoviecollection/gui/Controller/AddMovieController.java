/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.Controller;

import java.awt.FileDialog;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javax.swing.JFrame;
import org.controlsfx.control.CheckComboBox;
import privatemoviecollection.be.Movie;
import privatemoviecollection.gui.Model.PMCModel;

/**
 * FXML Controller class
 *
 * @author Revy
 */
public class AddMovieController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField pathField;
    @FXML
    private TextField personalField;
    private PMCModel model;
    private Movie selectedMovie;
    private MainWindowController mwController;
    @FXML
    private CheckComboBox<?> categoryBox;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      model = PMCModel.getInstance();
      mwController = new MainWindowController();
      if (model.getSelectedMovie() != null)
      {
          selectedMovie = model.getSelectedMovie();
          nameField.setText(selectedMovie.getName());
          ratingField.setText(String.valueOf(selectedMovie.getRating()));
          pathField.setText(selectedMovie.getFilelink());
          personalField.setText(String.valueOf(selectedMovie.getPersonalrating()));
      }
    }    

    @FXML
    private void clickToPickFile(ActionEvent event) {
        FileDialog fd = new FileDialog(new JFrame());
        fd.setVisible(true);
        if(fd.getFile()!=null){
        File[] f = fd.getFiles();
        String filePath = fd.getFiles()[0].getPath();
        pathField.setText(filePath);
        }
        else System.out.println("file not chosen");
        }

    @FXML
    private void acceptButtonMethod(ActionEvent event) {
        String name = nameField.getText();
        float rating = Float.parseFloat(ratingField.getText());
        String path = pathField.getText();
        float personalRating = Float.valueOf(personalField.getText());
        
        if (!model.isEdit())
        {
            System.out.println(name + " " + rating + " " + path + " " + personalRating);
            int id = model.getHighestIDofMovies();
            model.addMovie(name, rating, path, personalRating, id);
        } else if (model.isEdit())
        {
            Movie m = new Movie(name, rating, personalRating, path, selectedMovie.getId());
            model.editMovie(m);
        }
        
        mwController.setSongsTable();
        ((Node) (event.getSource())).getScene().getWindow().hide();

        
    }
    
    public void setController(MainWindowController controller) 
    {
        this.mwController = controller;
    }
    
    
    
    }
    

