package privatemoviecollection.gui.Model;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.bll.PMCLogicFacade;
import privatemoviecollection.bll.PMCManager;
import privatemoviecollection.gui.Controller.MainWindowController;

public class PMCModel
{
    private final PMCLogicFacade logicFacade;
    public static PMCModel instance;
    private Movie movie;
    private Media hit;
    private MediaPlayer mediaPlayer;
    private boolean edit;
    
    public static PMCModel getInstance() 
    {
        if (instance == null)
        {
            try {
                instance = new PMCModel();
            } catch (IOException ex) {
                Logger.getLogger(PMCModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(PMCModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }
    
    public PMCModel() throws IOException, SQLException
    {
        logicFacade = new PMCManager();
    }
    
    public Movie addMovie(String name, float rating, String path, float personalPath,int id)
    {
        return logicFacade.addMovie(name, rating, path, personalPath, id);
    }
    
    public List getMovies(){
        return  logicFacade.getMovies();
    }
    
    public List getCategories(){
        return logicFacade.getCategories();
    }
    
    public void removeMovie(Movie movie)
    {
        logicFacade.removeMovie(movie);
    }
    
    public void addCategory(Category category)
    {
        logicFacade.addCategory(category);
    }
    
    public void removeCategory(Category category)
    {
        logicFacade.removeCategory(category);
    }
    
    public void setCategory(Movie movie, Category category)
    {
        logicFacade.setCategory(movie, category);
    }
    
    public Movie searchMovie(String quote)
    {
        return logicFacade.searchMovie(quote);
    }
    
    public void addPersonalRating(float personalRating, Movie movie)
    {
        logicFacade.addPersonalRating(personalRating, movie);
    }
    
    public void removePersonalRating(Movie movie)
    {
        logicFacade.removePersonalRating(movie);
    }
    
    public void saveMoviesInDatabase()
    {
        logicFacade.saveMoviesInDatabase();
    }
    
    public void setSelectedMovie(Movie movie)
    {
        this.movie = movie;
    }
    
    public Movie getSelectedMovie()
    {
        return movie;
    }
    
    public int getHighestIDofMovies(){
    return logicFacade.getHighestIDofMovies();
    }
    
    public void setMediaPlayer(Movie movie) 
    {
        
        String moviePath = movie.getFilelink();
        moviePath = moviePath.replace("\\", "\\\\");
        hit = new Media(new File(moviePath).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setAutoPlay(true);
        
       
    }
    
    public MediaPlayer getMediaPlayer()
    {
        return mediaPlayer;
    }
    
    public void openWindow(String path, Parent root1) // opens up a window and sets the connection
    {
        try {
//            Parent root1;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            root1 = (Parent) fxmlLoader.load();
//            fxmlLoader.<AddMovieController>getController().setController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editMovie(Movie movie)
    {
        logicFacade.editMovie(movie);
    }

    public boolean isEdit()
    {
        return edit;
    }

    public void setEdit(boolean edit)
    {
        this.edit = edit;
    }
}
