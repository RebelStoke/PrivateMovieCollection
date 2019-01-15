/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.Controller;

import java.awt.FileDialog;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javax.swing.JFrame;
import org.controlsfx.control.CheckComboBox;
import privatemoviecollection.be.Category;
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
    private ObservableList<Category> categories;
    @FXML
    private CheckComboBox<Category> categoryBox;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      model = PMCModel.getInstance();
      mwController = new MainWindowController();
      categories = FXCollections.observableArrayList(model.getCategories());
      categoryBox.getItems().addAll(categories);
      if (model.getSelectedMovie() != null)
      {
          selectedMovie = model.getSelectedMovie();
          nameField.setText(selectedMovie.getName());
          ratingField.setText(String.valueOf(selectedMovie.getRating()));
          pathField.setText(selectedMovie.getFilelink());
          personalField.setText(String.valueOf(selectedMovie.getPersonalrating()));
          ObservableList<Category> ob = selectedMovie.getCategories();
          for (Category object : ob) {
              categoryBox.getCheckModel().check(object);
          }
          
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
        boolean isMovieCorrect=true;
        boolean titleCorrect=true;
        List<Movie> allMovies;
        allMovies=model.getMovies();
        String name = nameField.getText();
        float rating =0;
        rating = Float.parseFloat(ratingField.getText());
        String path = pathField.getText();
        float personalRating=0;
        personalRating = Float.valueOf(personalField.getText());
        int id = model.getHighestIDofMovies();
        
        for (Movie movie : allMovies) {
            if(movie.getName().equals(name))
                titleCorrect=false;
        }
        
        if(titleCorrect && rating<10.0 && personalRating<10.0)
            isMovieCorrect=true;
        else
            isMovieCorrect=false;
        
        if(isMovieCorrect){
        Movie m = model.addMovie(name, rating, path, personalRating, id);
        mwController.setSongsTable();
        ObservableList<Category> cat = categoryBox.getCheckModel().getCheckedItems();
        for (Category category : cat) {
            System.out.println(category);
        }
        m.setCategories(cat);
        }
        isMovieCorrect=true;
        titleCorrect=true;
       ((Node) (event.getSource())).getScene().getWindow().hide();

        
    }
    
    public void setController(MainWindowController controller) 
    {
        this.mwController = controller;
    }
    
    
    
    }
    

