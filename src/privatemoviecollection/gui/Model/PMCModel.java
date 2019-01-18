package privatemoviecollection.gui.Model;

import java.io.IOException;
import java.util.List;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.bll.BLLException;
import privatemoviecollection.bll.PMCLogicFacade;
import privatemoviecollection.bll.PMCManager;
import privatemoviecollection.dal.DALException;

public class PMCModel {

    private final PMCLogicFacade logicFacade;
    public static PMCModel instance;
    private Movie movie;
    private boolean edit;
    
    public static PMCModel getInstance() throws ModelException {
        if (instance == null) {
                instance = new PMCModel();
        }
        return instance;
    }
    
    public PMCModel() throws ModelException {
        try
        {
            logicFacade = new PMCManager();
        } catch (DALException ex)
        {
            throw new ModelException(ex);
        } catch (IOException ex)
        {
            throw new ModelException(ex);
        }
    }
    
    public Movie addMovie(String name, float rating, String path, float personalPath, int id) {
        return logicFacade.addMovie(name, rating, path, personalPath, id);
    }
    
    public List<Movie> getMovies() {
        return logicFacade.getMovies();
    }
    
    public List<Category> getCategories() {
        return logicFacade.getCategories();
    }
    
    public void removeMovie(Movie movie) {
        logicFacade.removeMovie(movie);
    }
    
    public void setCategory(Movie movie, Category category) {
        logicFacade.setCategory(movie, category);
    }
    
    public void addPersonalRating(float personalRating, Movie movie) {
        logicFacade.addPersonalRating(personalRating, movie);
    }
    
    public void removePersonalRating(Movie movie) {
        logicFacade.removePersonalRating(movie);
    }
    
    public void saveMoviesInDatabase() throws ModelException {
       
        try
        {
            logicFacade.saveMoviesInDatabase();
        } catch (BLLException ex)
        {
            throw new ModelException(ex);
        }
        
    }
    
    public void setSelectedMovie(Movie movie) {
        this.movie = movie;
    }
    
    public Movie getSelectedMovie() {
        return movie;
    }
    
    public int getHighestIDofMovies() {
        return logicFacade.getHighestIDofMovies();
    }
    
    public boolean isEdit() {
        return edit;
    }
    
    public void setEdit(boolean edit) {
        this.edit = edit;
    }
}
