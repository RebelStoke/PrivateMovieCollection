/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
public class MainWindowController implements Initializable {

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
    @FXML
    private TextField searchField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Remember to Delete outdated AND badly rated movies From the Database for an up-to-date Database!", ButtonType.OK);
        alert.showAndWait();
        try {
            model = PMCModel.getInstance();
        } catch (ModelException ex) {
            newAlert(ex);
        }
        setMoviesTable(model.getMovies());

    }

    public void setMoviesTable(List<Movie> list) // This method gets all songs from database and loeads it into tableSongs
    {
        moviesAsObservable = FXCollections.observableArrayList(list);

        titleCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("categoriesAsString"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        ratingCol.getStyleClass().add("column-without-left-border");
        ratingCol.getStyleClass().add("time-col");
        tableOfMovies.getColumns().clear();
        tableOfMovies.setItems(moviesAsObservable);
        tableOfMovies.getColumns().addAll(titleCol, categoryCol, ratingCol);
    }

    @FXML
    private void addMovieMethod(ActionEvent event) {
        Movie m = null;
        model.setSelectedMovie(m);
        model.setEdit(false);
        openWindow();
    }

    @FXML
    private void exitButtonMethod(ActionEvent event) {
        try {
            model.saveMoviesInDatabase();
            System.exit(1);
        } catch (ModelException ex) {
            newAlert(ex);
        }

    }

    @FXML
    private void minimizeButtonMethod(ActionEvent event) {
        Stage stage = (Stage) add.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void deleteMovieMethod(ActionEvent event) {
        Movie m = tableOfMovies.getSelectionModel().getSelectedItem();
        model.removeMovie(m);
        setMoviesTable(model.getMovies());
    }

    @FXML
    private void editMovieMethod(ActionEvent event) {
        Movie m = tableOfMovies.getSelectionModel().getSelectedItem();
        if (m != null) {
            model.setSelectedMovie(m);
            model.setEdit(true);
            openWindow();
        } else {
            newAlert(new Exception("Please select a movie!"));
        }
    }

    @FXML
    private void playMedia(MouseEvent event) {
        if (tableOfMovies.getSelectionModel().getSelectedItem() != null) {
            play();
        } else {
            newAlert(new Exception("Please select a movie!"));
        }
    }

    @FXML
    private void doubleClick(MouseEvent event) {
        if (event.getClickCount() == 2 && tableOfMovies.getSelectionModel().getSelectedItem() != null) {
            try {
                Movie movie = tableOfMovies.getSelectionModel().getSelectedItem();
//            model.setSelectedMovie(movie);
//            String path = "/privatemoviecollection/gui/View/movieDetails.fxml";
//            model.openWindow(path, root1);
                ObservableList<Category> categories = movie.getCategories();
                String title = movie.getName();
                float rating = movie.getRating();
                float personalrating = movie.getPersonalrating();
                String lastview = checkLastView(movie.getLastview() + "");
                System.out.println(lastview.length());
                Parent root3;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/View/movieDetails.fxml"));
                root3 = (Parent) fxmlLoader.load();
                fxmlLoader.<MovieDetailsController>getController().setController(this);
                fxmlLoader.<MovieDetailsController>getController().passInfo(title, categories, rating, personalrating, lastview);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root3));
                stage.centerOnScreen();
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                newAlert(ex);
            }

        }

    }

    public String checkLastView(String s) {
        if (s.equals("null")) {
            return "Never opened";
        } else {
            return s;
        }
    }

    public void play() {

        try {
            Movie movie = tableOfMovies.getSelectionModel().getSelectedItem();
            if (movie.getFilelink().contains(".mp4") || movie.getFilelink().contains(".mpeg4")) {
                Parent root2;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/View/mediaPlayer.fxml"));
                root2 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root2));
                stage.centerOnScreen();
                stage.show();
                fxmlLoader.<MediaPlayerController>getController().setController(this, movie);
                
            }
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            newAlert(ex);
        }

    }

    private void openWindow() {
        try {
            Parent root3;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/privatemoviecollection/gui/View/addMovie.fxml"));
            root3 = (Parent) fxmlLoader.load();
            fxmlLoader.<AddMovieController>getController().setController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root3));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            newAlert(ex);
        }
    }

    private void newAlert(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage(), ButtonType.OK);
        a.show();
    }

    public void search() {
        String txt = searchField.getText();
        txt = txt.toLowerCase();
        List<Movie> list1 = model.getMovies();
        List<Movie> list2 = new ArrayList<Movie>();
        if (txt.contains(","))
        {
            int ind = txt.indexOf(",");
            String quo1 = txt.substring(0, ind);
            String quo2 = txt.substring(ind + 1).trim();
            for (Movie movie : list1)
            {
                if (movie.getCategoriesAsString().toLowerCase().contains(quo1) || movie.getCategoriesAsString().toLowerCase().contains(quo2))
                {
                    list2.add(movie);
                }
            }
        } else
        {
        for (Movie movie : list1) {
            if (movie.getName().toLowerCase().contains(txt) || movie.getCategoriesAsString().toLowerCase().contains(txt) || movie.getRating() >= Float.valueOf(txt)) {
                list2.add(movie);
            }
        }
        }
        moviesAsObservable = FXCollections.observableArrayList(list2);
        setMoviesTable
        (list2);
    }

    @FXML
    private void enterSearch(KeyEvent event) // executes search method by clicking enter
    {
        if (event.getCode() == KeyCode.ENTER && searchField.isFocused()) {
            search();
        }
    }

    public void showStage() {
        Stage stage = (Stage) add.getScene().getWindow();
        stage.show();
    }

    public void hideStage() {
        Stage stage = (Stage) add.getScene().getWindow();
        stage.hide();
    }
}
