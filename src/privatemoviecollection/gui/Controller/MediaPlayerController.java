/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.gui.Controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import privatemoviecollection.be.Movie;

/**
 * FXML Controller class
 *
 * @author Revy
 */
public class MediaPlayerController implements Initializable {

    private MainWindowController mwController;
    private Movie movie;
    private MediaPlayer mediaPlayer;
    private Media hit;
    @FXML
    private MediaView mediaView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

private void setMusicPlayer() 
    {
        
        String moviePath = movie.getFilelink();
        moviePath = moviePath.replace("\\", "\\\\");
        System.out.println(moviePath);
        hit = new Media(new File(moviePath).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        
        mediaPlayer.play();
       
    }
void setController(MainWindowController controller, Movie movie){
        this.mwController = controller;
        this.movie = movie;
        setMusicPlayer();
}

    @FXML
    private void exitButton(ActionEvent event) {
        mediaPlayer.stop();
        
    }
    
}
