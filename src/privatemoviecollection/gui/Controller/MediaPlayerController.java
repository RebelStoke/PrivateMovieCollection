/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.Controller;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import privatemoviecollection.be.Movie;
import privatemoviecollection.gui.Model.ModelException;
import privatemoviecollection.gui.Model.PMCModel;

/**
 * FXML Controller class
 *
 * @author Revy
 */
public class MediaPlayerController implements Initializable {

    private MainWindowController mwController;
    private PMCModel model;
    private Movie movie;
    private MediaPlayer mediaPlayer;
    private Media hit;
    @FXML
    private MediaView mediaView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            model = PMCModel.getInstance();
        } catch (ModelException ex) {
            newAlert(ex);
        }
        movie = model.getSelectedMovie();
    }

    private void setMusicPlayer() {

        String moviePath = movie.getFilelink();
        moviePath = moviePath.replace("\\", "\\\\");
        System.out.println(moviePath);
        hit = new Media(new File(moviePath).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
        Date date = Date.valueOf(LocalDate.now());
        movie.setLastview(date);
    }

    void setController(MainWindowController controller, Movie movie) {
        this.mwController = controller;
        this.movie = movie;
        setMusicPlayer();
    }

    @FXML
    private void exitButton(ActionEvent event) {
        mediaPlayer.stop();
        mwController.showStage();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    private void playButton(MouseEvent event) {
        mediaPlayer.play();
    }

    @FXML
    private void pauseButton(MouseEvent event) {
        mediaPlayer.pause();
    }

    private void newAlert(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occured: " + ex, ButtonType.OK);
        a.show();
    }

}
