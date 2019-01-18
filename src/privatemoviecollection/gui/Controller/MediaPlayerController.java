package privatemoviecollection.gui.Controller;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    @FXML
    private Label actualDuration;
    @FXML
    private Label movieDuration;
    private Stage stage;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Slider movieLengthSlider;
    private int j = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            model = PMCModel.getInstance();
        } catch (ModelException ex) {
            newAlert(ex);
        }
        movieLengthSlider.setMin(0);
        movieLengthSlider.setMax(1.0);
        movieLengthSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
                -> {
            progressBar.setProgress(newValue.doubleValue());
            Duration duration = Duration.seconds(mediaPlayer.getTotalDuration().toSeconds() * newValue.doubleValue());
            mediaPlayer.seek(duration);

        });
        movie = model.getSelectedMovie();
    }

    private void setMusicPlayer() {
        String moviePath = movie.getFilelink();
        moviePath = moviePath.replace("\\", "\\\\");
        hit = new Media(new File(moviePath).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        stage.show();
        mwController.hideStage();
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                setTime();
                mediaPlayer.play();
                Date date = Date.valueOf(LocalDate.now());
                movie.setLastview(date);

            }
        });
    }

    void setController(MainWindowController controller, Movie movie) {
        stage = (Stage) actualDuration.getScene().getWindow();
        stage.hide();
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
    private void pauseButton(MouseEvent event) {
        mediaPlayer.pause();
    }

    private void newAlert(Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, "An error occured: " + ex.getMessage(), ButtonType.OK);
        a.show();
    }

    @FXML
    private void continueButton(MouseEvent event) {
        mediaPlayer.play();
    }

    @FXML
    private void previousButton(MouseEvent event) {
        try {
            int i = model.getMovies().indexOf(movie);
            movie = (Movie) model.getMovies().get(i - 1);
            setMusicPlayer();
        } catch (Exception e) {
            newAlert(new Exception("No previous movie."));
        }
    }

    @FXML
    private void nextButton(MouseEvent event) {
        try {
            int i = model.getMovies().indexOf(movie);
            movie = (Movie) model.getMovies().get(i + 1);
            setMusicPlayer();
        } catch (Exception e) {
            newAlert(new Exception("No more movies."));
        }

    }

    private class progressUpdate implements Runnable {

        @Override
        public void run() {
            while (true) {
                Platform.runLater(()
                        -> {

                    int i = (int) mediaPlayer.getCurrentTime().toSeconds();
                    String txt = "";
                    if ((i / 60) < 10) {
                        txt += "0" + i / 60 + ":";
                    } else {
                        txt += i / 60 + ":";
                    }
                    if ((i % 60) < 10) {
                        txt += "0" + i % 60;
                    } else {
                        txt += i % 60;
                    }
                    actualDuration.setText(txt);
                    progressBar.setProgress(mediaPlayer.getCurrentTime().toSeconds() / mediaPlayer.getTotalDuration().toSeconds());
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MediaPlayerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void setTime() {
        j = (int) mediaPlayer.getTotalDuration().toSeconds();
        String txt = "";
        if ((j / 60) < 10) {
            txt += "0" + j / 60 + ":";
        } else {
            txt += j / 60 + ":";
        }
        if ((j % 60) < 10) {
            txt += "0" + j % 60;
        } else {
            txt += j % 60;
        }
        movieDuration.setText(txt);
        Runnable runnable = new progressUpdate();
        Thread thread = new Thread(runnable);
        thread.start();

    }

}
