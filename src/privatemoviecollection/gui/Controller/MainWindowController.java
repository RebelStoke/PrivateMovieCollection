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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.gui.Model.ModelException;
import privatemoviecollection.gui.Model.PMCModel;

/**
 * FXML Controller class
 *
 * @author Revy
 */
public class MainWindowController implements Initializable
{

    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button edit;
    private PMCModel model;
    @FXML
    private TableView<Movie> tableOfMovies;

    private ObservableList moviesAsObservable;
    @FXML
    private TableColumn<Movie, String> titleCol;
    @FXML
    private TableColumn<Movie, String> categoryCol;
    @FXML
    private TableColumn<Movie, Float> ratingCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Remember to Delete outdated AND badly rated movies From the Database for an up-to-date Database!", ButtonType.OK);
        alert.showAndWait();
        try
        {
            model = PMCModel.getInstance();
        } catch (ModelException ex)
        {
            newAlert(ex);
        }
//        try
//        {
//            String name = "";
//            Desktop.getDesktop().open(new File(name));
//        } catch (IOException ex)
//        {
//            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        moviesAsObservable = FXCollections.observableArrayList();
        setSongsTable();

    }

    public void setSongsTable() // This method gets all songs from database and loeads it into tableSongs
    {
        moviesAsObservable = FXCollections.observableArrayList(model.getMovies());

        titleCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("categoriesAsString"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));

        tableOfMovies.getColumns().clear();
        tableOfMovies.setItems(moviesAsObservable);
        tableOfMovies.getColumns().addAll(titleCol, categoryCol, ratingCol);
    }

    @FXML
    private void addMovieMethod(ActionEvent event)
    {
        Movie m = null;
        model.setSelectedMovie(m);
        model.setEdit(false);
        openWindow();
    }

    @FXML
    private void exitButtonMethod(ActionEvent event)
    {
        try
        {
            model.saveMoviesInDatabase();
            System.exit(1);
        } catch (ModelException ex)
        {
            newAlert(ex);
        }

    }

    @FXML
    private void minimizeButtonMethod(ActionEvent event)
    {
    }

    @FXML
    private void deleteMovieMethod(ActionEvent event)
    {
        Movie m = tableOfMovies.getSelectionModel().getSelectedItem();
        model.removeMovie(m);
        setSongsTable();
    }

    @FXML
    private void editMovieMethod(ActionEvent event)
    {
        Movie m = tableOfMovies.getSelectionModel().getSelectedItem();
        if (m != null) {
        model.setSelectedMovie(m);
        model.setEdit(true);
        openWindow();
        } else
        {
            newAlert(new Exception("Please select a movie!"));
        }
    }

    @FXML
    private void playMedia(MouseEvent event)
    {
        if (tableOfMovies.getSelectionModel().getSelectedItem() != null)
        {
        play();
        } else
        {
            newAlert(new Exception("Please select a movie!"));
        }
    }

    @FXML
    private void doubleClick(MouseEvent event)
    {
        if (event.getClickCount() == 2 && tableOfMovies.getSelectionModel().getSelectedItem() != null)
        {
            try
            {
                Movie movie = tableOfMovies.getSelectionModel().getSelectedItem();
//            model.setSelectedMovie(movie);
//            String path = "/privatemoviecollection/gui/View/movieDetails.fxml";
//            model.openWindow(path, root1);
                ObservableList<Category> categories = movie.getCategories();
                String title = movie.getName();
                String rating = Float.toString(movie.getRating());
                Parent root3;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/View/movieDetails.fxml"));
                root3 = (Parent) fxmlLoader.load();
                fxmlLoader.<MovieDetailsController>getController().setController(this);
                fxmlLoader.<MovieDetailsController>getController().passInfo(title, categories, rating);
                Stage stage = new Stage();
                stage.setScene(new Scene(root3));
                stage.centerOnScreen();
                stage.show();
            } catch (IOException ex)
            {
                Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                newAlert(ex);
            }

        }

    }

    public void play()
    {

        try
        {
            Movie movie = tableOfMovies.getSelectionModel().getSelectedItem();
//            model.setSelectedMovie(movie);
//            String path = "/privatemoviecollection/gui/View/mediaPlayer.fxml";
//            model.openWindow(path, root1);
            Parent root2;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/View/mediaPlayer.fxml"));
            root2 = (Parent) fxmlLoader.load();
            fxmlLoader.<MediaPlayerController>getController().setController(this, movie);
            Stage stage = new Stage();
            stage.setScene(new Scene(root2));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ex)
        {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            newAlert(ex);
        }

    }

    private void openWindow()
    {
        try
        {
            Parent root3;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/View/addMovie.fxml"));
            root3 = (Parent) fxmlLoader.load();
            fxmlLoader.<AddMovieController>getController().setController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root3));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ex)
        {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            newAlert(ex);
        }
    }

    private void newAlert(Exception ex)
    {
        Alert a = new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage(), ButtonType.OK);
        a.show();
    }
}
