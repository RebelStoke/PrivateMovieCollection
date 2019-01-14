package privatemoviecollection.gui.Model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.bll.PMCLogicFacade;
import privatemoviecollection.bll.PMCManager;

public class PMCModel
{
    private final PMCLogicFacade logicFacade;
    public static PMCModel instance;
    private Movie movie;
    
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
}
