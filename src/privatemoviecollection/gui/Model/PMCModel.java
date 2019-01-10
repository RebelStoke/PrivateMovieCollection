package privatemoviecollection.gui.Model;

import java.io.IOException;
import java.sql.SQLException;
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
    
    public void addMovie(String name, float rating, String path, float personalPath)
    {
        logicFacade.addMovie(name, rating, path, personalPath);
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
}
